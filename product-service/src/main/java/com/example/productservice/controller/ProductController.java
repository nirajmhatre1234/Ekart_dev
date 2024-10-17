package com.example.productservice.controller;

import com.example.productservice.dto.ProductReponse;
import com.example.productservice.entity.Product;
import com.example.productservice.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public List<ProductReponse>getAllProduct() throws JsonProcessingException {
        List<ProductReponse> ProductResponseList = productService.getAllProducts();
        return ProductResponseList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReponse> getProduct(@PathVariable Long id) {
        ProductReponse product = productService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping("/")
    public ResponseEntity<ProductReponse> createProduct(@RequestBody Product product) throws IOException {
        ProductReponse productResponse = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @DeleteMapping("/{id}")
    public void removeProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductReponse> editProduct(@PathVariable Long id, @RequestBody Product product) {

        ProductReponse productReponse = productService.editProduct(id, product);

        if (productReponse.getProductId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productReponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}

