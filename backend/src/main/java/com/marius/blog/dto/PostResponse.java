package com.marius.blog.dto;

import com.marius.blog.model.Post.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "Response object for a blog post")
public class PostResponse {
    
    @Schema(description = "Post ID", example = "1")
    private Long id;
    
    @Schema(description = "Post title", example = "The Impact of John Williams' Scores in Star Wars")
    private String title;
    
    @Schema(description = "Post slug (URL-friendly)", example = "impact-john-williams-star-wars")
    private String slug;
    
    @Schema(description = "Post content")
    private String content;
    
    @Schema(description = "Post excerpt")
    private String excerpt;
    
    @Schema(description = "Featured image URL")
    private String featuredImageUrl;
    
    @Schema(description = "Post status")
    private PostStatus status;
    
    @Schema(description = "Author information")
    private AuthorInfo author;
    
    @Schema(description = "Categories")
    private Set<CategoryInfo> categories;
    
    @Schema(description = "Tags")
    private Set<TagInfo> tags;
    
    @Schema(description = "Views count")
    private Long viewsCount;
    
    @Schema(description = "Published date")
    private LocalDateTime publishedAt;
    
    @Schema(description = "Created date")
    private LocalDateTime createdAt;
    
    @Schema(description = "Updated date")
    private LocalDateTime updatedAt;
    
    public PostResponse() {}
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
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
    
    public AuthorInfo getAuthor() {
        return author;
    }
    
    public void setAuthor(AuthorInfo author) {
        this.author = author;
    }
    
    public Set<CategoryInfo> getCategories() {
        return categories;
    }
    
    public void setCategories(Set<CategoryInfo> categories) {
        this.categories = categories;
    }
    
    public Set<TagInfo> getTags() {
        return tags;
    }
    
    public void setTags(Set<TagInfo> tags) {
        this.tags = tags;
    }
    
    public Long getViewsCount() {
        return viewsCount;
    }
    
    public void setViewsCount(Long viewsCount) {
        this.viewsCount = viewsCount;
    }
    
    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
    
    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Inner classes for nested objects
    @Schema(description = "Author information")
    public static class AuthorInfo {
        private Long id;
        private String username;
        private String avatarUrl;
        
        public AuthorInfo() {}
        
        public AuthorInfo(Long id, String username, String avatarUrl) {
            this.id = id;
            this.username = username;
            this.avatarUrl = avatarUrl;
        }
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getAvatarUrl() {
            return avatarUrl;
        }
        
        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
    
    @Schema(description = "Category information")
    public static class CategoryInfo {
        private Long id;
        private String name;
        private String slug;
        
        public CategoryInfo() {}
        
        public CategoryInfo(Long id, String name, String slug) {
            this.id = id;
            this.name = name;
            this.slug = slug;
        }
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getSlug() {
            return slug;
        }
        
        public void setSlug(String slug) {
            this.slug = slug;
        }
    }
    
    @Schema(description = "Tag information")
    public static class TagInfo {
        private Long id;
        private String name;
        private String slug;
        
        public TagInfo() {}
        
        public TagInfo(Long id, String name, String slug) {
            this.id = id;
            this.name = name;
            this.slug = slug;
        }
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getSlug() {
            return slug;
        }
        
        public void setSlug(String slug) {
            this.slug = slug;
        }
    }
}
