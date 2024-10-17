package com.example.productservice.service;

import com.example.productservice.dto.CategoryResponse;
import com.example.productservice.dto.ProductReponse;
import com.example.productservice.entity.Product;
import com.example.productservice.exception.DuplicateProductException;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.mapper.ProductMapper;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.restcalls.CategoryRestCalls;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;



@Service
public class ProductService {

    private ProductRepository productRepository;

    ProductMapper productMapper;

    CategoryRestCalls categoryRestCalls;


    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper,
                          CategoryRestCalls categoryRestCalls) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRestCalls = categoryRestCalls;
    }

    //@Cacheable(value = "products",key = "#id")
    @CircuitBreaker(name = "default", fallbackMethod = "categoriesFallbackMethod")
    @RateLimiter(name = "default", fallbackMethod = "fallbackCallExternalService")
    public ProductReponse getProduct(Long id) {

        Product product = new Product();

        try {
            product = productRepository.findById(id).orElse(null);

            long categoryId = product.getCategory_id();

            CategoryResponse categoryResponse = categoryRestCalls.getProductCategory(categoryId);

            ProductReponse productReponse = productMapper.toProductResponse(product);
            productReponse.setCategory(categoryResponse);

            return productReponse;
        }
        catch (NullPointerException e) {
           throw new ProductNotFoundException("Product not available: " + id);
        }

    }

    public List<ProductReponse> getAllProducts() throws JsonProcessingException {

        List<Product> products = productRepository.findAll();

        List<ProductReponse>  productReponseList = new ArrayList<>();

        for (Product product : products) {

            CategoryResponse categoryResponse = categoryRestCalls.getProductCategory(product.getCategory_id());
            ProductReponse productReponse = productMapper.toProductResponse(product);

            productReponse.setCategory(categoryResponse);
            productReponseList.add(productReponse);
        }

        return productReponseList;
    }

    @CachePut(value = "products", key = "#product.productId")
    public ProductReponse saveProduct(Product product) throws IOException {

        product.setCreated_date(new Date());
        product.setUpdated_date(new Date());

        long categoryId = product.getCategory_id();

        CategoryResponse categoryResponse = categoryRestCalls.getProductCategory(categoryId);

        try {
            Product product1 = productRepository.save(product);
        }
        catch (DataIntegrityViolationException e) {
            throw new DuplicateProductException("Product with the same name already exists.");
        }

        ProductReponse productReponse = productMapper.toProductResponse(product);
        productReponse.setCategory(categoryResponse);

        return productReponse;

    }

    @CacheEvict(value = "products",key = "#id")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @CachePut(value = "products", key = "#product.productId")
    public ProductReponse editProduct(Long id, Product product) {

        // Find the product by ID
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            // Update fields
            Product updatedProduct = existingProduct.get();

            updatedProduct.setCategory_id(product.getCategory_id());
            updatedProduct.setProductName(product.getProductName());
            updatedProduct.setProductDescription(product.getProductDescription());
            updatedProduct.setProductPrice(product.getProductPrice());
            updatedProduct.setProductQuantity(product.getProductQuantity());
            updatedProduct.setUpdated_date(new Date());

            productRepository.save(updatedProduct);

            ProductReponse productReponse = productMapper.toProductResponse(updatedProduct);
            return productReponse;
        }
        return new ProductReponse(); // Product not found
    }

    // Fallback response
    public ProductReponse categoriesFallbackMethod(Long id, Throwable throwable) {
        System.out.println("Fallback response for category ID: " + id + " Error : " + throwable.getMessage());
        return new ProductReponse();
    }

    public ProductReponse fallbackCallExternalService(Long id, Throwable throwable){
        System.out.println("Fallback response due to rate limit exceeded. Category Id : " + id + " Error : " + throwable.getMessage());
        return new ProductReponse();
    }
}
