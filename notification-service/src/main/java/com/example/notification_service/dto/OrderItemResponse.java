package com.example.notification_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Component
public class OrderItemResponse  implements Serializable {

    private Long orderItemId;

    private Long productId;

    private String productName;

    private Long quantity;

    private Double price;

}
