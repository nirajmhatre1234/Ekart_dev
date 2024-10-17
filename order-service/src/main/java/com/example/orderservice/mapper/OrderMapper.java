package com.example.orderservice.mapper;


import com.example.orderservice.dto.OrderItemResponse;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponse toOrderResponse(Order order, List<OrderItemResponse> orderItemResponseList) {
        return OrderResponse.builder()
                .order_id(order.getId())
                .order_date(order.getOrderDate())
                .customer_id(order.getCustomerId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .orderItems(orderItemResponseList)
                .build();
    }

    public Order toOrder(OrderResponse orderResponse) {
        return Order.builder()
                .id(orderResponse.getOrder_id())
                .orderDate(orderResponse.getOrder_date())
                .customerId(orderResponse.getCustomer_id())
                .status(orderResponse.getStatus())
                .orderItems(orderResponse.getOrderItems().stream().map(orderItemResponse -> toOrderItem(orderItemResponse)).toList())
                .totalAmount(orderResponse.getTotalAmount())
                .build();
    }

    public OrderItem toOrderItem(OrderItemResponse orderItem) {
        return OrderItem.builder()
                .orderItemId(orderItem.getOrderItemId())
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .order(null)
                .build();
    }

    public OrderItemResponse toOrderItemResponse(OrderItem orderItem, ProductResponse productResponse) {
        return OrderItemResponse.builder()
                .orderItemId(orderItem.getOrderItemId())
                .productId(orderItem.getProductId())
                .productName(productResponse.getProductName())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public Product toProduct(ProductResponse productReponse) {
        return Product.builder()
                .productId(productReponse.getProductId())
                .productDescription(productReponse.getProductDescription())
                .productName(productReponse.getProductName())
                .productPrice(productReponse.getProductPrice())
                .productQuantity(productReponse.getProductQuantity())
                .category_id(productReponse.getCategory_id())
                .build();
    }
}
