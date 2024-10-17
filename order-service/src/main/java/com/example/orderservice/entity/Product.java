package com.example.orderservice.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Product {

    private Long productId;

    private String productName;

    private String productDescription;

    private Double productPrice;

    private Long productQuantity;

    private Long category_id;
}
