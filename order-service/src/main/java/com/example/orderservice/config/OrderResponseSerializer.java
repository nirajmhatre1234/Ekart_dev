package com.example.orderservice.config;

import com.example.orderservice.dto.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class OrderResponseSerializer implements Serializer<OrderResponse> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Configuration logic (if needed)
    }

    @Override
    public byte[] serialize(String topic, OrderResponse data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize OrderResponse to JSON", e);
        }
    }

    @Override
    public void close() {
        // Cleanup logic (if needed)
    }
}
