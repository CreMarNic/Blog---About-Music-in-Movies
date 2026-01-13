package com.marius.blog.repository;

import com.marius.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    Optional<Tag> findBySlug(String slug);
    
    Boolean existsBySlug(String slug);
    
    Boolean existsByName(String name);
}
