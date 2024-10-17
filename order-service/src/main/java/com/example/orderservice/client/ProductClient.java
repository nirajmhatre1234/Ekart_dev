package com.example.orderservice.client;

import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("product-service")
public interface ProductClient {

    @GetMapping("/product/{id}")
    ResponseEntity<ProductResponse> getProduct(@PathVariable Long id);

    @PutMapping("/product/{id}")
    ResponseEntity<ProductResponse> editProduct(@PathVariable Long id, @RequestBody Product product);

    @PostMapping("/product/")
    ResponseEntity<ProductResponse> createProduct(@RequestBody Product product);
}
