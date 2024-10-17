package com.example.notification_service.dto;

import com.example.notification_service.enums.OrderStatus;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Component
public class OrderResponse  implements Serializable {

    private Long order_id;

    private Long customer_id;

    private Date order_date;

    private OrderStatus status;

    private Double totalAmount;

    private List<OrderItemResponse> orderItems;
}
