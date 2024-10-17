package com.example.categoryservice.dto;

import com.example.categoryservice.entity.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.io.Serializable;
import java.util.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoryResponse implements Serializable{

    private Long id;

    private String name;

    @JsonBackReference
    private Category category;

    @JsonManagedReference
    private List<CategoryResponse> subcategories = new ArrayList<>();
}
