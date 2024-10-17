package com.example.orderservice.dto;

import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItemResponse {

    private Long orderItemId;

    private Long productId;

    private String productName;

    private Long quantity;

    private Double price;

}
