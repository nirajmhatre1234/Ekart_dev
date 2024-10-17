package com.example.notification_service.config;

import com.example.notification_service.dto.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class OrderResponseDeserializer implements Deserializer<OrderResponse> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Configuration logic (if needed)
    }

    @Override
    public OrderResponse deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, OrderResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON to OrderResponse", e);
        }
    }

    @Override
    public void close() {
    }
}

