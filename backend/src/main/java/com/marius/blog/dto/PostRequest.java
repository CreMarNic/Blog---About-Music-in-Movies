package com.marius.blog.dto;

import com.marius.blog.model.Post.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "Request object for creating or updating a post")
public class PostRequest {
    
    @NotBlank(message = "Title is required")
    @Schema(description = "Post title", example = "The Impact of John Williams' Scores in Star Wars", required = true)
    private String title;
    
    @Schema(description = "Post content (HTML/rich text)", example = "<p>Content here...</p>")
    private String content;
    
    @Schema(description = "Post excerpt/summary", example = "Exploring the iconic music of Star Wars...")
    private String excerpt;
    
    @Schema(description = "Featured image URL", example = "https://example.com/image.jpg")
    private String featuredImageUrl;
    
    @Schema(description = "Post status", example = "PUBLISHED")
    private PostStatus status = PostStatus.DRAFT;
    
    @Schema(description = "Category IDs", example = "[1, 2]")
    private Set<Long> categoryIds;
    
    @Schema(description = "Tag IDs", example = "[1, 2, 3]")
    private Set<Long> tagIds;
    
    @Schema(description = "Published date (optional)")
    private LocalDateTime publishedAt;
    
    public PostRequest() {}
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getExcerpt() {
        return excerpt;
    }
    
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
    
    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }
    
    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }
    
    public PostStatus getStatus() {
        return status;
    }
    
    public void setStatus(PostStatus status) {
        this.status = status;
    }
    
    public Set<Long> getCategoryIds() {
        return categoryIds;
    }
    
    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }
    
    public Set<Long> getTagIds() {
        return tagIds;
    }
    
    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }
    
    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
    
    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
}
