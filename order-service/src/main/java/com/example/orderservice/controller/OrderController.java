package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.producer.MessageProducer;
import com.example.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderService orderService;

    @Autowired
    MessageProducer messageProducer;


    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        OrderResponse orderResponse = orderService.getOrder(id);
        return orderResponse;
    }

    @GetMapping("/customer-id/{id}")
    public List<OrderResponse>getAllOrder(@PathVariable Long id) {
        List<OrderResponse> orderResponseList = orderService.getAllOrders(id);
        return orderResponseList;
    }

    @PostMapping("/")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody Order order) {

        System.out.println("New Order Received.");

        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(order); // Set the reference here
        }

        OrderResponse orderResponse = orderService.saveOrder(order);

        messageProducer.sendOrder("ekart-notification", orderResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {

        OrderResponse orderResponse = orderService.getOrder(id);
        OrderResponse cancelledOrderResponse =  orderService.cancelOrder(orderResponse);

        messageProducer.sendOrder("ekart-notification", cancelledOrderResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(cancelledOrderResponse);
    }

}

