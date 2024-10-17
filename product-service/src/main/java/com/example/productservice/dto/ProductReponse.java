package com.example.productservice.dto;

import com.example.productservice.entity.Category;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductReponse {

    private Long productId;

    private String productName;

    private String productDescription;

    private Double productPrice;

    private Long productQuantity;

    private Date created_date;

    private Date updated_date;

    private CategoryResponse category;
}
