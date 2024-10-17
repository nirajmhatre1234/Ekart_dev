package com.example.orderservice;

import com.example.orderservice.client.OrderClient;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    OrderClient orderClient;

    @Test
    public void testPlaceOrderLoad() throws InterruptedException {
        int numberOfRequests = 10;
        int concurrentThreads = 50; // Adjust based on your system capabilities
        ExecutorService executor = Executors.newFixedThreadPool(concurrentThreads);
        List<Runnable> tasks = new ArrayList<>();
        final List<String> responses = new ArrayList<>(); // To collect responses

        for (int i = 0; i < numberOfRequests; i++) {
            final int orderId = i; // Unique order ID for each task
            tasks.add(() -> {
                Order order = createOrder(orderId);

                try {
                    ResponseEntity<OrderResponse> orderResponseEntity = orderClient.placeOrder(order);
                    responses.add("Order ID: " + orderId + " - Response Status: " + orderResponseEntity.getStatusCode());
                } catch (Exception e) {
                    responses.add("Order ID: " + orderId + " - Error: " + e.getMessage());
                }
            });
        }

        // Execute tasks in parallel
        for (Runnable task : tasks) {
            executor.submit(task);
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES); // Wait for all tasks to finish

        // Print results
        responses.forEach(System.out::println);
    }

    private Order createOrder(int orderId) {
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.builder().productId(3L).quantity(1L).build();
        orderItemList.add(orderItem);

        return Order.builder()
                .customerId((long) orderId)
                .orderDate(new Date())
                .status(OrderStatus.PENDING)
                .totalAmount(0.0)
                .orderItems(orderItemList)
                .build();
    }
}
