package com.marius.blog.controller;

import com.marius.blog.dto.PostRequest;
import com.marius.blog.dto.PostResponse;
import com.marius.blog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
@Tag(name = "Posts", description = "Blog post management endpoints")
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @Operation(summary = "Get all published posts (public)", description = "Returns paginated list of published posts")
    @GetMapping("/public")
    public ResponseEntity<Page<PostResponse>> getAllPublishedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(postService.getAllPublishedPosts(pageable));
    }
    
    @Operation(summary = "Get post by slug (public)", description = "Returns a published post by its slug")
    @GetMapping("/public/slug/{slug}")
    public ResponseEntity<PostResponse> getPostBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(postService.getPostBySlug(slug));
    }
    
    @Operation(summary = "Search posts (public)", description = "Search published posts by title or content")
    @GetMapping("/public/search")
    public ResponseEntity<Page<PostResponse>> searchPosts(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.searchPosts(q, pageable));
    }
    
    @Operation(summary = "Get posts by category (public)", description = "Returns published posts in a category")
    @GetMapping("/public/category/{categorySlug}")
    public ResponseEntity<Page<PostResponse>> getPostsByCategory(
            @PathVariable String categorySlug,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getPostsByCategory(categorySlug, pageable));
    }
    
    @Operation(summary = "Get posts by tag (public)", description = "Returns published posts with a tag")
    @GetMapping("/public/tag/{tagSlug}")
    public ResponseEntity<Page<PostResponse>> getPostsByTag(
            @PathVariable String tagSlug,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getPostsByTag(tagSlug, pageable));
    }
    
    @Operation(summary = "Create a new post", description = "Creates a new blog post (AUTHOR/ADMIN only)")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        PostResponse response = postService.createPost(request, userDetails);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Get my posts", description = "Returns all posts by the authenticated user")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/my-posts")
    public ResponseEntity<Page<PostResponse>> getMyPosts(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getMyPosts(userDetails, pageable));
    }
    
    @Operation(summary = "Get post by ID", description = "Returns a post by its ID")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }
    
    @Operation(summary = "Update a post", description = "Updates an existing post (author or admin only)")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(postService.updatePost(id, request, userDetails));
    }
    
    @Operation(summary = "Delete a post", description = "Deletes a post (author or admin only)")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePost(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
