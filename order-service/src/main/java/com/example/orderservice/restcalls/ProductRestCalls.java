package com.example.orderservice.restcalls;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.entity.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;


@Component
public class ProductRestCalls {

    @Autowired
    ProductClient productClient;


    // Rest Call to get Product by id
    @CircuitBreaker(name = "ProductRestCalls", fallbackMethod = "fallbackCallExternalService")
    public ProductResponse getProduct(long productId) {
        ResponseEntity<ProductResponse> productResponse = productClient.getProduct(productId);
        return productResponse.getBody();
    }

    // Rest Call to update Product by id
    public void updateProduct(long productId, Product product) {

        ResponseEntity<ProductResponse> responseResponseEntity = productClient.editProduct(productId,product);
        ProductResponse productResponse = responseResponseEntity.getBody();

        if (responseResponseEntity.getStatusCode() == HttpStatus.OK){
            System.out.println("Product updated successfully");
        }
        else {
            System.out.println("Product update failed");
        }
    }

    public String fallbackCallExternalService(long id,Exception e) {

        System.out.println("Fallback response: Service is currently unavailable.");
        return "Fallback response: Service is currently unavailable.";
    }

}
