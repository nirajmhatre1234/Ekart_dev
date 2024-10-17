package com.example.productservice.restcalls;


import com.example.productservice.client.CategoryClient;
import com.example.productservice.dto.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryRestCalls {

    @Autowired
    private CategoryClient categoryClient;

    public CategoryResponse getProductCategory(long categoryId) {
        ResponseEntity<CategoryResponse> responseEntity = categoryClient.getCategory(categoryId);
        System.out.println("REST CALL RESPONSE : " + responseEntity.toString());
        return responseEntity.getBody();
    }

}

