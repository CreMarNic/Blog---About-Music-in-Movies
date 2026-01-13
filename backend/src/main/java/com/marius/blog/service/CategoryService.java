package com.marius.blog.service;

import com.marius.blog.dto.CategoryRequest;
import com.marius.blog.dto.CategoryResponse;
import com.marius.blog.exception.BadRequestException;
import com.marius.blog.exception.ResourceNotFoundException;
import com.marius.blog.model.Category;
import com.marius.blog.repository.CategoryRepository;
import com.marius.blog.util.SlugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new BadRequestException("Category name already exists");
        }
        
        String slug = SlugUtil.toSlug(request.getName());
        if (categoryRepository.existsBySlug(slug)) {
            slug = generateUniqueSlug(slug);
        }
        
        Category category = new Category();
        category.setName(request.getName());
        category.setSlug(slug);
        category.setDescription(request.getDescription());
        
        category = categoryRepository.save(category);
        return convertToResponse(category);
    }
    
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        return convertToResponse(category);
    }
    
    public CategoryResponse getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + slug));
        return convertToResponse(category);
    }
    
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        
        if (request.getName() != null && !request.getName().equals(category.getName())) {
            if (categoryRepository.existsByName(request.getName())) {
                throw new BadRequestException("Category name already exists");
            }
            category.setName(request.getName());
            String slug = SlugUtil.toSlug(request.getName());
            if (categoryRepository.existsBySlug(slug) && !slug.equals(category.getSlug())) {
                slug = generateUniqueSlug(slug);
            }
            category.setSlug(slug);
        }
        
        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }
        
        category = categoryRepository.save(category);
        return convertToResponse(category);
    }
    
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        categoryRepository.delete(category);
    }
    
    private String generateUniqueSlug(String baseSlug) {
        String slug = baseSlug;
        int counter = 1;
        
        while (categoryRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        
        return slug;
    }
    
    private CategoryResponse convertToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setSlug(category.getSlug());
        response.setDescription(category.getDescription());
        response.setCreatedAt(category.getCreatedAt());
        return response;
    }
}
