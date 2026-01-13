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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    @Transactional
    public PostResponse createPost(PostRequest request, UserDetails userDetails) {
        User author = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Check if user has permission (AUTHOR or ADMIN)
        if (author.getRole() != User.Role.AUTHOR && author.getRole() != User.Role.ADMIN) {
            throw new UnauthorizedException("Only authors and admins can create posts");
        }
        
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setSlug(generateUniqueSlug(request.getTitle()));
        post.setContent(request.getContent());
        post.setExcerpt(request.getExcerpt());
        post.setFeaturedImageUrl(request.getFeaturedImageUrl());
        post.setStatus(request.getStatus());
        post.setAuthor(author);
        post.setPublishedAt(request.getPublishedAt());
        
        // Set categories
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            Set<Category> categories = new HashSet<>();
            for (Long categoryId : request.getCategoryIds()) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));
                categories.add(category);
            }
            post.setCategories(categories);
        }
        
        // Set tags
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (Long tagId : request.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + tagId));
                tags.add(tag);
            }
            post.setTags(tags);
        }
        
        post = postRepository.save(post);
        return convertToResponse(post);
    }
    
    @Transactional
    public PostResponse updatePost(Long id, PostRequest request, UserDetails userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + id));
        
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Check if user is the author or an admin
        if (!post.getAuthor().getId().equals(currentUser.getId()) && 
            currentUser.getRole() != User.Role.ADMIN) {
            throw new UnauthorizedException("You don't have permission to update this post");
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
        
        // Update categories
        if (request.getCategoryIds() != null) {
            Set<Category> categories = new HashSet<>();
            for (Long categoryId : request.getCategoryIds()) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));
                categories.add(category);
            }
            post.setCategories(categories);
        }
        
        // Update tags
        if (request.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>();
            for (Long tagId : request.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + tagId));
                tags.add(tag);
            }
            post.setTags(tags);
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
    
    public Page<PostResponse> getPostsByCategory(String categorySlug, Pageable pageable) {
        return postRepository.findByStatusAndCategory(Post.PostStatus.PUBLISHED, categorySlug, pageable)
                .map(this::convertToResponse);
    }
    
    public Page<PostResponse> getPostsByTag(String tagSlug, Pageable pageable) {
        return postRepository.findByStatusAndTag(Post.PostStatus.PUBLISHED, tagSlug, pageable)
                .map(this::convertToResponse);
    }
    
    public Page<PostResponse> searchPosts(String query, Pageable pageable) {
        return postRepository.searchPublishedPosts(Post.PostStatus.PUBLISHED, query, pageable)
                .map(this::convertToResponse);
    }
    
    public Page<PostResponse> getMyPosts(UserDetails userDetails, Pageable pageable) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return postRepository.findByAuthorId(user.getId(), pageable)
                .map(this::convertToResponse);
    }
    
    @Transactional
    public void deletePost(Long id, UserDetails userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + id));
        
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Check if user is the author or an admin
        if (!post.getAuthor().getId().equals(currentUser.getId()) && 
            currentUser.getRole() != User.Role.ADMIN) {
            throw new UnauthorizedException("You don't have permission to delete this post");
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
        
        // Set categories
        if (post.getCategories() != null) {
            Set<PostResponse.CategoryInfo> categoryInfos = post.getCategories().stream()
                    .map(cat -> new PostResponse.CategoryInfo(cat.getId(), cat.getName(), cat.getSlug()))
                    .collect(Collectors.toSet());
            response.setCategories(categoryInfos);
        }
        
        // Set tags
        if (post.getTags() != null) {
            Set<PostResponse.TagInfo> tagInfos = post.getTags().stream()
                    .map(tag -> new PostResponse.TagInfo(tag.getId(), tag.getName(), tag.getSlug()))
                    .collect(Collectors.toSet());
            response.setTags(tagInfos);
        }
        
        return response;
    }
}
