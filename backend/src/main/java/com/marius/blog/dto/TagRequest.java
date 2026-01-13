package com.marius.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request object for creating or updating a tag")
public class TagRequest {
    
    @NotBlank(message = "Tag name is required")
    @Schema(description = "Tag name", example = "John Williams", required = true)
    private String name;
    
    public TagRequest() {}
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
