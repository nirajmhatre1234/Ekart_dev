package com.example.orderservice.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoryResponse {

    private Long id;

    private String name;

    private List<CategoryResponse> subcategories = new ArrayList<>();
}
