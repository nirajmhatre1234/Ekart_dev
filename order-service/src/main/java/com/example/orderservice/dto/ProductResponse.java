package com.example.orderservice.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductResponse{

    private Long productId;

    private String productName;

    private String productDescription;

    private Double productPrice;

    private Long productQuantity;

    private Long category_id;

    private CategoryResponse category;

}
