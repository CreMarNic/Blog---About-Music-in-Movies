package com.marius.blog.service;

import com.marius.blog.dto.PostRequest;
import com.marius.blog.dto.PostResponse;
import com.marius.blog.exception.ResourceNotFoundException;
import com.marius.blog.exception.UnauthorizedException;
import com.marius.blog.model.*;
import com.marius.blog.repository.*;
import com.marius.blog.util.SlugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public PostResponse createPost(PostRequest request, UserDetails userDetails) {
        User author = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setSlug(generateUniqueSlug(request.getTitle()));
        post.setContent(request.getContent());
        post.setExcerpt(request.getExcerpt());
        post.setFeaturedImageUrl(request.getFeaturedImageUrl());
        post.setStatus(request.getStatus());
        post.setAuthor(author);
        post.setPublishedAt(request.getPublishedAt());
        
        post = postRepository.save(post);
        return convertToResponse(post);
    }
    
    @Transactional
    public PostResponse updatePost(Long id, PostRequest request, UserDetails userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + id));
        
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        if (currentUser.getRole() != User.Role.ADMIN) {
            throw new UnauthorizedException("Only admins can update posts");
        }
        
        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
            // Only update slug if title changed
            if (!post.getTitle().equals(request.getTitle())) {
                post.setSlug(generateUniqueSlug(request.getTitle()));
            }
        }
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        if (request.getExcerpt() != null) {
            post.setExcerpt(request.getExcerpt());
        }
        if (request.getFeaturedImageUrl() != null) {
            post.setFeaturedImageUrl(request.getFeaturedImageUrl());
        }
        if (request.getStatus() != null) {
            post.setStatus(request.getStatus());
        }
        if (request.getPublishedAt() != null) {
            post.setPublishedAt(request.getPublishedAt());
        }
        
        post = postRepository.save(post);
        return convertToResponse(post);
    }
    
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + id));
        
        // Increment views
        post.setViewsCount(post.getViewsCount() + 1);
        postRepository.save(post);
        
        return convertToResponse(post);
    }
    
    public PostResponse getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + slug));
        
        // Only return published posts for public access
        if (post.getStatus() != Post.PostStatus.PUBLISHED) {
            throw new ResourceNotFoundException("Post not found: " + slug);
        }
        
        // Increment views
        post.setViewsCount(post.getViewsCount() + 1);
        postRepository.save(post);
        
        return convertToResponse(post);
    }
    
    public Page<PostResponse> getAllPublishedPosts(Pageable pageable) {
        return postRepository.findByStatus(Post.PostStatus.PUBLISHED, pageable)
                .map(this::convertToResponse);
    }
    
    public Page<PostResponse> searchPosts(String query, Pageable pageable) {
        return postRepository.searchPublishedPosts(Post.PostStatus.PUBLISHED, query, pageable)
                .map(this::convertToResponse);
    }
    
    public Page<PostResponse> getMyPosts(UserDetails userDetails, Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(this::convertToResponse);
    }
    
    @Transactional
    public void deletePost(Long id, UserDetails userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + id));
        
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        if (currentUser.getRole() != User.Role.ADMIN) {
            throw new UnauthorizedException("Only admins can delete posts");
        }
        
        postRepository.delete(post);
    }
    
    private String generateUniqueSlug(String title) {
        String baseSlug = SlugUtil.toSlug(title);
        String slug = baseSlug;
        int counter = 1;
        
        while (postRepository.findBySlug(slug).isPresent()) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        
        return slug;
    }
    
    private PostResponse convertToResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setSlug(post.getSlug());
        response.setContent(post.getContent());
        response.setExcerpt(post.getExcerpt());
        response.setFeaturedImageUrl(post.getFeaturedImageUrl());
        response.setStatus(post.getStatus());
        response.setViewsCount(post.getViewsCount());
        response.setPublishedAt(post.getPublishedAt());
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());
        
        // Set author
        PostResponse.AuthorInfo authorInfo = new PostResponse.AuthorInfo(
                post.getAuthor().getId(),
                post.getAuthor().getUsername(),
                post.getAuthor().getAvatarUrl()
        );
        response.setAuthor(authorInfo);
        
        return response;
    }
}
