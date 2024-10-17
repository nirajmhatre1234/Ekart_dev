package com.example.orderservice.dto;

import com.example.orderservice.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderResponse {

    private Long order_id;

    private Long customer_id;

    private Date order_date;

    private OrderStatus status;

    private Double totalAmount;

    private List<OrderItemResponse> orderItems;
}
