package com.marius.blog.dto;

import com.marius.blog.model.Comment.CommentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Response object for a comment")
public class CommentResponse {
    
    @Schema(description = "Comment ID", example = "1")
    private Long id;
    
    @Schema(description = "Comment content")
    private String content;
    
    @Schema(description = "Author information")
    private AuthorInfo author;
    
    @Schema(description = "Author name (for anonymous comments)")
    private String authorName;
    
    @Schema(description = "Comment status")
    private CommentStatus status;
    
    @Schema(description = "Parent comment ID")
    private Long parentId;
    
    @Schema(description = "Replies to this comment")
    private List<CommentResponse> replies;
    
    @Schema(description = "Created date")
    private LocalDateTime createdAt;
    
    @Schema(description = "Updated date")
    private LocalDateTime updatedAt;
    
    public CommentResponse() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public AuthorInfo getAuthor() {
        return author;
    }
    
    public void setAuthor(AuthorInfo author) {
        this.author = author;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public CommentStatus getStatus() {
        return status;
    }
    
    public void setStatus(CommentStatus status) {
        this.status = status;
    }
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public List<CommentResponse> getReplies() {
        return replies;
    }
    
    public void setReplies(List<CommentResponse> replies) {
        this.replies = replies;
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
}
