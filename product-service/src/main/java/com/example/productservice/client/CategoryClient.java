package com.example.productservice.client;

import com.example.productservice.dto.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service")
public interface CategoryClient {

    @GetMapping("/category/{id}")
    ResponseEntity<CategoryResponse> getCategory(@PathVariable long id);


}
