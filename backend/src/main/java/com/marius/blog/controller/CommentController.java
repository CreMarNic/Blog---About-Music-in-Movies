package com.marius.blog.controller;

import com.marius.blog.dto.CommentRequest;
import com.marius.blog.dto.CommentResponse;
import com.marius.blog.model.Comment;
import com.marius.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
@Tag(name = "Comments", description = "Comment management endpoints")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @Operation(summary = "Get comments by post (public)", description = "Returns approved comments for a post")
    @GetMapping("/public/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostWithReplies(postId));
    }
    
    @Operation(summary = "Create a comment", description = "Creates a new comment on a post")
    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        CommentResponse response = commentService.createComment(postId, request, userDetails);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Update comment status", description = "Moderates a comment (ADMIN or post author only)")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}/status")
    public ResponseEntity<CommentResponse> updateCommentStatus(
            @PathVariable Long id,
            @RequestParam Comment.CommentStatus status,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(commentService.updateCommentStatus(id, status, userDetails));
    }
    
    @Operation(summary = "Delete a comment", description = "Deletes a comment (ADMIN, post author, or comment author only)")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        commentService.deleteComment(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
