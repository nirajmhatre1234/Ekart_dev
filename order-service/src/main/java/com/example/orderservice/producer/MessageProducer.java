package com.example.orderservice.producer;

import com.example.orderservice.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String,OrderResponse> kafkaTemplate;

    public void sendOrder(String topicName, OrderResponse orderResponse) {
        kafkaTemplate.send(topicName, orderResponse);
    }
}
