package com.example.categoryservice.controller;

import com.example.categoryservice.dto.CategoryResponse;
import com.example.categoryservice.entity.Category;
import com.example.categoryservice.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

   @Autowired
   CategoryService categoryService;

    @GetMapping("/")
    public List<CategoryResponse>getAllCategory() {
        List<CategoryResponse> categoryResponseList = categoryService.getAllCategory();
        return categoryResponseList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable long id) {
        CategoryResponse categoryResponse = categoryService.getCategory(id);
        return ResponseEntity.ok(categoryResponse);
    }

    @PostMapping("/")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(category));
    }

    @DeleteMapping("/{id}")
    public void removeCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }

}
