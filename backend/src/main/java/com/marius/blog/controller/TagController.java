package com.marius.blog.controller;

import com.marius.blog.dto.TagRequest;
import com.marius.blog.dto.TagResponse;
import com.marius.blog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "*")
@Tag(name = "Tags", description = "Tag management endpoints")
public class TagController {
    
    @Autowired
    private TagService tagService;
    
    @Operation(summary = "Get all tags (public)", description = "Returns all tags")
    @GetMapping("/public")
    public ResponseEntity<List<TagResponse>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }
    
    @Operation(summary = "Get tag by slug (public)", description = "Returns a tag by its slug")
    @GetMapping("/public/slug/{slug}")
    public ResponseEntity<TagResponse> getTagBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(tagService.getTagBySlug(slug));
    }
    
    @Operation(summary = "Create a new tag", description = "Creates a new tag (AUTHOR/ADMIN only)")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<TagResponse> createTag(@Valid @RequestBody TagRequest request) {
        TagResponse response = tagService.createTag(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Get tag by ID", description = "Returns a tag by its ID")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }
    
    @Operation(summary = "Update a tag", description = "Updates an existing tag (AUTHOR/ADMIN only)")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagRequest request) {
        return ResponseEntity.ok(tagService.updateTag(id, request));
    }
    
    @Operation(summary = "Delete a tag", description = "Deletes a tag (AUTHOR/ADMIN only)")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
