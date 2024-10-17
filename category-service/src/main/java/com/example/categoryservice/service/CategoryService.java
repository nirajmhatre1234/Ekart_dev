package com.example.categoryservice.service;

import com.example.categoryservice.dto.CategoryResponse;
import com.example.categoryservice.entity.Category;
import com.example.categoryservice.exception.CategoryNotFoundException;
import com.example.categoryservice.mapper.CategoryMapper;
import com.example.categoryservice.repository.CategoryRepository;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    public CategoryResponse getCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getAllCategory() {
        List<Category> categorys = categoryRepository.findAll();
        return categorys.stream().map(categoryMapper::toCategoryResponse).toList();
    }

    public Category saveCategory(Category category) {
        // Save the parent category first to generate its ID
        Category savedCategory = categoryRepository.save(category);

        // If the category has subcategories, set their parent to the saved category
        if (category.getSubcategories() != null) {
            for (Category subcategory : category.getSubcategories()) {
                subcategory.setParent(savedCategory); // Set the saved category as the parent
            }
        }

        // Save subcategories
        if (category.getSubcategories() != null) {
            savedCategory.setSubcategories(category.getSubcategories()); // Update saved category's subcategories
            categoryRepository.save(savedCategory); // Save again to persist changes
        }

        // Return the saved category with its subcategories
        return savedCategory;
    }

    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
