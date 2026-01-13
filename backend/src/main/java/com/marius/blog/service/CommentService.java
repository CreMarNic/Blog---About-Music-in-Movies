package com.marius.blog.service;

import com.marius.blog.dto.CommentRequest;
import com.marius.blog.dto.CommentResponse;
import com.marius.blog.exception.ResourceNotFoundException;
import com.marius.blog.exception.UnauthorizedException;
import com.marius.blog.model.Comment;
import com.marius.blog.model.Post;
import com.marius.blog.model.User;
import com.marius.blog.repository.CommentRepository;
import com.marius.blog.repository.PostRepository;
import com.marius.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public CommentResponse createComment(Long postId, CommentRequest request, UserDetails userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + postId));
        
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(request.getContent());
        
        // Set user if authenticated, otherwise use anonymous info
        if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            comment.setUser(user);
            comment.setStatus(Comment.CommentStatus.APPROVED); // Auto-approve logged-in users
        } else {
            comment.setAuthorName(request.getAuthorName());
            comment.setAuthorEmail(request.getAuthorEmail());
            comment.setStatus(Comment.CommentStatus.PENDING); // Require moderation for anonymous
        }
        
        // Handle nested comments
        if (request.getParentId() != null) {
            Comment parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found: " + request.getParentId()));
            comment.setParent(parent);
        }
        
        comment = commentRepository.save(comment);
        return convertToResponse(comment);
    }
    
    public Page<CommentResponse> getCommentsByPost(Long postId, Pageable pageable) {
        return commentRepository.findByPostIdAndStatus(postId, Comment.CommentStatus.APPROVED, pageable)
                .map(this::convertToResponse);
    }
    
    public List<CommentResponse> getCommentsByPostWithReplies(Long postId) {
        List<Comment> topLevelComments = commentRepository.findByPostIdAndStatusAndParentIsNull(
                postId, Comment.CommentStatus.APPROVED);
        
        return topLevelComments.stream()
                .map(comment -> convertToResponseWithReplies(comment))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public CommentResponse updateCommentStatus(Long id, Comment.CommentStatus status, UserDetails userDetails) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found: " + id));
        
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Only admins and post authors can moderate comments
        if (currentUser.getRole() != User.Role.ADMIN && 
            !comment.getPost().getAuthor().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You don't have permission to moderate comments");
        }
        
        comment.setStatus(status);
        comment = commentRepository.save(comment);
        return convertToResponse(comment);
    }
    
    @Transactional
    public void deleteComment(Long id, UserDetails userDetails) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found: " + id));
        
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Only admins, post authors, or comment authors can delete
        boolean canDelete = currentUser.getRole() == User.Role.ADMIN ||
                           comment.getPost().getAuthor().getId().equals(currentUser.getId()) ||
                           (comment.getUser() != null && comment.getUser().getId().equals(currentUser.getId()));
        
        if (!canDelete) {
            throw new UnauthorizedException("You don't have permission to delete this comment");
        }
        
        commentRepository.delete(comment);
    }
    
    private CommentResponse convertToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setStatus(comment.getStatus());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(comment.getUpdatedAt());
        
        if (comment.getUser() != null) {
            CommentResponse.AuthorInfo authorInfo = new CommentResponse.AuthorInfo(
                    comment.getUser().getId(),
                    comment.getUser().getUsername(),
                    comment.getUser().getAvatarUrl()
            );
            response.setAuthor(authorInfo);
        } else {
            response.setAuthorName(comment.getAuthorName());
        }
        
        if (comment.getParent() != null) {
            response.setParentId(comment.getParent().getId());
        }
        
        return response;
    }
    
    private CommentResponse convertToResponseWithReplies(Comment comment) {
        CommentResponse response = convertToResponse(comment);
        
        List<Comment> replies = commentRepository.findByParentId(comment.getId());
        List<CommentResponse> replyResponses = replies.stream()
                .filter(reply -> reply.getStatus() == Comment.CommentStatus.APPROVED)
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        
        response.setReplies(replyResponses);
        return response;
    }
}
