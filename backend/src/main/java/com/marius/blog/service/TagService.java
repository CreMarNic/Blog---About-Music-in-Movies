package com.marius.blog.service;

import com.marius.blog.dto.TagRequest;
import com.marius.blog.dto.TagResponse;
import com.marius.blog.exception.BadRequestException;
import com.marius.blog.exception.ResourceNotFoundException;
import com.marius.blog.model.Tag;
import com.marius.blog.repository.TagRepository;
import com.marius.blog.util.SlugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    
    @Autowired
    private TagRepository tagRepository;
    
    @Transactional
    public TagResponse createTag(TagRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new BadRequestException("Tag name already exists");
        }
        
        String slug = SlugUtil.toSlug(request.getName());
        if (tagRepository.existsBySlug(slug)) {
            slug = generateUniqueSlug(slug);
        }
        
        Tag tag = new Tag();
        tag.setName(request.getName());
        tag.setSlug(slug);
        
        tag = tagRepository.save(tag);
        return convertToResponse(tag);
    }
    
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public TagResponse getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        return convertToResponse(tag);
    }
    
    public TagResponse getTagBySlug(String slug) {
        Tag tag = tagRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + slug));
        return convertToResponse(tag);
    }
    
    @Transactional
    public TagResponse updateTag(Long id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        
        if (request.getName() != null && !request.getName().equals(tag.getName())) {
            if (tagRepository.existsByName(request.getName())) {
                throw new BadRequestException("Tag name already exists");
            }
            tag.setName(request.getName());
            String slug = SlugUtil.toSlug(request.getName());
            if (tagRepository.existsBySlug(slug) && !slug.equals(tag.getSlug())) {
                slug = generateUniqueSlug(slug);
            }
            tag.setSlug(slug);
        }
        
        tag = tagRepository.save(tag);
        return convertToResponse(tag);
    }
    
    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        tagRepository.delete(tag);
    }
    
    private String generateUniqueSlug(String baseSlug) {
        String slug = baseSlug;
        int counter = 1;
        
        while (tagRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        
        return slug;
    }
    
    private TagResponse convertToResponse(Tag tag) {
        TagResponse response = new TagResponse();
        response.setId(tag.getId());
        response.setName(tag.getName());
        response.setSlug(tag.getSlug());
        response.setCreatedAt(tag.getCreatedAt());
        return response;
    }
}
