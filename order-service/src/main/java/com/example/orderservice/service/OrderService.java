package com.example.orderservice.service;

import com.example.orderservice.dto.OrderItemResponse;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.entity.Product;
import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.restcalls.ProductRestCalls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ProductRestCalls productRestCalls;

    // Get Order Details with orderId
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id).get();
        OrderResponse orderResponse = mappedOrderToOrderResponse(order);
        return orderResponse;
    }

    // Get All Order Details with customerId
    @Cacheable(value = "orders", key = "#id")
    public List<OrderResponse> getAllOrders(Long id) {
        List<Order> orderList = orderRepository.findOrdersByCustomerId(id);
        List<OrderResponse> orderResponseList = orderList.stream().map(order -> mappedOrderToOrderResponse(order)).toList();
        return orderResponseList;
    }

    @CachePut(value = "orders", key = "#order.id")
    public OrderResponse saveOrder(Order order) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItem orderItem : order.getOrderItems()) {

            ProductResponse productResponse =  productRestCalls.getProduct(orderItem.getProductId());

            if (productResponse.getProductQuantity() > 0){
                orderItem.setPrice(productResponse.getProductPrice());
                orderItems.add(orderItem);

                Product product = orderMapper.toProduct(productResponse);
                product.setProductPrice(productResponse.getProductPrice());
                product.setProductQuantity(product.getProductQuantity() - orderItem.getQuantity());
                product.setCategory_id(productResponse.getCategory().getId());
                product.setProductName(productResponse.getProductName());
                product.setProductDescription(productResponse.getProductDescription());

                productRestCalls.updateProduct(orderItem.getProductId(), product);
            }
            else {
                System.out.println("Message : { \n "
                        + "Product Name : "  + productResponse.getProductName() + "\n"
                        + "Product Quantity : " + productResponse.getProductQuantity() + "\n"
                        +" Product Out of Stock. }");
            }

        }

        order.setOrderDate(new Date());
        order.setOrderItems(orderItems);

        Double totalAmount = order.getOrderItems().stream().mapToDouble(m-> m.getPrice() * m.getQuantity()).sum();
        order.setTotalAmount(totalAmount);

        orderRepository.save(order);

        return mappedOrderToOrderResponse(order);
    }

    @CacheEvict(value = "orders", key = "#orderResponse.order_id")
    public OrderResponse cancelOrder(OrderResponse orderResponse) {

        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemResponse> orderItemsResponses = new ArrayList<>();

        Order order = orderMapper.toOrder(orderResponse);
        order.setStatus(OrderStatus.CANCELLED);

        for (OrderItem orderItem : order.getOrderItems()) {

            ProductResponse productResponse = productRestCalls.getProduct(orderItem.getProductId());
            productResponse.setCategory_id(productResponse.getCategory().getId());

            Product product = orderMapper.toProduct(productResponse);
            product.setProductQuantity(product.getProductQuantity() + orderItem.getQuantity());

            productRestCalls.updateProduct(product.getProductId(), product);

            orderItem.setOrder(order);
            orderItems.add(orderItem);

            orderItemsResponses.add(orderMapper.toOrderItemResponse(orderItem,productResponse));
        }

        order.setOrderItems(orderItems);



        orderRepository.save(order);

        OrderResponse orderCanceledResponse = orderMapper.toOrderResponse(order,orderItemsResponses);
        System.out.println("orderCanceledResponse : " + orderCanceledResponse.getStatus());
        return orderCanceledResponse;
    }


    private OrderResponse mappedOrderToOrderResponse(Order order) {

        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();

        for (OrderItem orderItem : orderItems){
            ProductResponse productResponse = productRestCalls.getProduct(orderItem.getProductId());
            OrderItemResponse orderItemResponse = orderMapper.toOrderItemResponse(orderItem,productResponse);
            orderItemResponseList.add(orderItemResponse);
        }

        OrderResponse orderResponse = orderMapper.toOrderResponse(order,orderItemResponseList);

        return orderResponse;
    }
}
