package com.marius.blog.repository;

import com.marius.blog.model.Post;
import com.marius.blog.model.Post.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    Optional<Post> findBySlug(String slug);
    
    Page<Post> findByStatus(PostStatus status, Pageable pageable);
    
    Page<Post> findByAuthorId(Long authorId, Pageable pageable);
    
    Page<Post> findByStatusAndAuthorId(PostStatus status, Long authorId, Pageable pageable);
    
    @Query("SELECT p FROM Post p WHERE p.status = :status AND " +
           "(LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.content) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Post> searchPublishedPosts(@Param("status") PostStatus status, 
                                     @Param("query") String query, 
                                     Pageable pageable);
    
    @Query("SELECT p FROM Post p JOIN p.categories c WHERE p.status = :status AND c.slug = :categorySlug")
    Page<Post> findByStatusAndCategory(@Param("status") PostStatus status, 
                                        @Param("categorySlug") String categorySlug, 
                                        Pageable pageable);
    
    @Query("SELECT p FROM Post p JOIN p.tags t WHERE p.status = :status AND t.slug = :tagSlug")
    Page<Post> findByStatusAndTag(@Param("status") PostStatus status, 
                                   @Param("tagSlug") String tagSlug, 
                                   Pageable pageable);
}
