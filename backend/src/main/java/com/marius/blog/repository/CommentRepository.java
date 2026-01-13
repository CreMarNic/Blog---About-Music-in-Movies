package com.marius.blog.repository;

import com.marius.blog.model.Comment;
import com.marius.blog.model.Comment.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    
    Page<Comment> findByPostIdAndStatus(Long postId, CommentStatus status, Pageable pageable);
    
    List<Comment> findByPostIdAndStatusAndParentIsNull(Long postId, CommentStatus status);
    
    List<Comment> findByParentId(Long parentId);
    
    Page<Comment> findByStatus(CommentStatus status, Pageable pageable);
}
