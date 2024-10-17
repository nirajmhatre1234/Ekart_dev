package com.example.orderservice.client;

import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("order-service")
public interface OrderClient {

    @PostMapping("/order/")
    ResponseEntity<OrderResponse> placeOrder(@RequestBody Order order);
}
