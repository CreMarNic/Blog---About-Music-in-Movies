package com.marius.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request object for creating a comment")
public class CommentRequest {
    
    @NotBlank(message = "Comment content is required")
    @Schema(description = "Comment content", example = "Great article! I love this soundtrack.", required = true)
    private String content;
    
    @Schema(description = "Parent comment ID (for nested comments)", example = "1")
    private Long parentId;
    
    @Schema(description = "Author name (for anonymous comments)", example = "Anonymous")
    private String authorName;
    
    @Schema(description = "Author email (for anonymous comments)", example = "anonymous@example.com")
    private String authorEmail;
    
    public CommentRequest() {}
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public String getAuthorEmail() {
        return authorEmail;
    }
    
    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }
}
