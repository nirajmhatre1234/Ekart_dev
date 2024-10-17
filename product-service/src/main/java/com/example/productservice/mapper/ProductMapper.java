package com.example.productservice.mapper;


import com.example.productservice.dto.CategoryResponse;
import com.example.productservice.dto.ProductReponse;
import com.example.productservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductReponse toProductResponse(Product product) {
        return ProductReponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productDescription(product.getProductDescription())
                .productQuantity(product.getProductQuantity())
                .created_date(product.getCreated_date())
                .updated_date(product.getUpdated_date())
                .category(CategoryResponse.builder()
                        .id(product.getCategory_id())
                        .build())
                .build();
    }
}
