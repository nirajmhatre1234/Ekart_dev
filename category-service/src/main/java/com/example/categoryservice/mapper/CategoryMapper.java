package com.example.categoryservice.mapper;

import com.example.categoryservice.dto.CategoryResponse;
import com.example.categoryservice.entity.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryResponse toCategoryResponse(Category category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .category(category.getParent())
                .subcategories(category.getSubcategories().stream()
                        .map(this::toSubCategoryResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public CategoryResponse toSubCategoryResponse(Category category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .category(category.getParent())
                .build();
    }
}
