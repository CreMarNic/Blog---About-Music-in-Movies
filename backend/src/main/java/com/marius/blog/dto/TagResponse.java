package com.marius.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response object for a tag")
public class TagResponse {
    
    @Schema(description = "Tag ID", example = "1")
    private Long id;
    
    @Schema(description = "Tag name", example = "John Williams")
    private String name;
    
    @Schema(description = "Tag slug", example = "john-williams")
    private String slug;
    
    @Schema(description = "Created date")
    private LocalDateTime createdAt;
    
    public TagResponse() {}
    
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
