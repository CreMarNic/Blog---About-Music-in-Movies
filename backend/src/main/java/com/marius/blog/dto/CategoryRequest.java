package com.marius.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request object for creating or updating a category")
public class CategoryRequest {
    
    @NotBlank(message = "Category name is required")
    @Schema(description = "Category name", example = "Film Scores", required = true)
    private String name;
    
    @Schema(description = "Category description", example = "Exploring iconic film scores and soundtracks")
    private String description;
    
    public CategoryRequest() {}
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
