package com.example.orderservice.repository;

import com.example.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o INNER JOIN o.orderItems oi WHERE o.customerId = :id")
    List<Order> findOrdersByCustomerId(@Param("id") Long id);

    @Query("SELECT o FROM Order o INNER JOIN o.orderItems oi WHERE o.customerId = :id")
    Order findOrderByCustomerId(@Param("id") Long id);
}
