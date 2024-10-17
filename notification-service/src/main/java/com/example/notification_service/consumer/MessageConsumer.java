package com.example.notification_service.consumer;

import com.example.notification_service.constant.ApplicationConstant;
import com.example.notification_service.dto.OrderResponse;
import com.example.notification_service.enums.OrderStatus;
import com.example.notification_service.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MessageConsumer {

    @Autowired
    MailService mailService;

    private static final Logger logger = Logger.getLogger(String.valueOf(MessageConsumer.class));

    @KafkaListener(groupId = ApplicationConstant.GROUP_ID_JSON, topics = ApplicationConstant.TOPIC_NAME, containerFactory = ApplicationConstant.KAFKA_LISTENER_CONTAINER_FACTORY)
    public void receivedMessage(OrderResponse message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(message);

        System.out.println("Json message received using Kafka listener " + jsonString);

        if (message.getStatus().equals(OrderStatus.PENDING)){
            //mailService.sendMail("Pccenter_NirajM@nsdl.com","Your Order Has Been Successfully Placed!",message);
            System.out.println("Your Order Has Been Successfully Placed!");
        }
        else if (message.getStatus().equals(OrderStatus.CANCELLED)){
            //mailService.sendMail("Pccenter_NirajM@nsdl.com","Your Order Cancellation Confirmation",message);
            System.out.println("Your Order Cancellation Confirmation");
        }


    }
}

