package com.example.notification_service.service;

import com.example.notification_service.dto.OrderItemResponse;
import com.example.notification_service.dto.OrderResponse;
import com.example.notification_service.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    JavaMailSenderImpl mailSender;

    public void sendMail(String to, String subject, OrderResponse orderResponse){

        mailSender.setHost("172.19.45.53");
        mailSender.setPort(25);

        String mailBody = getMailTemplate(orderResponse);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("Pccenter_NirajM@nsdl.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(mailBody);

        System.out.println(mailBody);

        mailSender.send(simpleMailMessage);
    }

    private String getMailTemplate(OrderResponse orderResponse) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Hello [" + orderResponse.getCustomer_id() + "], \n \n");

        if (orderResponse.getStatus().equals(OrderStatus.PENDING)){

            stringBuilder.append("Thank you for your order! \n \n");
            stringBuilder.append("               Order Summary: \n");
            stringBuilder.append("               Order Number: " + orderResponse.getOrder_id() + "\n");
            stringBuilder.append("               Order Date: " + orderResponse.getOrder_date() + "\n \n");
            stringBuilder.append("               Items Ordered: \n");

            for (OrderItemResponse orderItemResponse : orderResponse.getOrderItems()){
                stringBuilder.append("               Product Name : " + orderItemResponse.getProductName() + "\n");
                stringBuilder.append("               Quantity: " + orderItemResponse.getQuantity() + "\n");
                stringBuilder.append("               Price : " + orderItemResponse.getPrice() + "\n \n");
            }

            stringBuilder.append("               Shipping Address : " + "\n \n");
            stringBuilder.append("If you have any questions about your order, please visit our Help page.\n" +
                    "      Best,\n" +
                    "      Ekart Customer Service");
        }
        else if (orderResponse.getStatus().equals(OrderStatus.CANCELLED)){

            stringBuilder.append("Thank you for your order with us! We appreciate your business and the trust you place in us. ");

            stringBuilder.append("We wanted to inform you that your order #[" + orderResponse.getOrder_id() + "] has been successfully canceled as per your request. \n" +
                    "If you have any questions or need further assistance, please don't hesitate to reach out.\n \n");
            stringBuilder.append("       Order Details: \n");
            stringBuilder.append("               Order Number: " + orderResponse.getOrder_id() + "\n");
            stringBuilder.append("               Order Date: " + orderResponse.getOrder_date() + "\n \n");
            stringBuilder.append("               Items Canceled: \n");

            for (OrderItemResponse orderItemResponse : orderResponse.getOrderItems()){
                stringBuilder.append("               Product Name : " + orderItemResponse.getProductName() + " - " + orderItemResponse.getQuantity() + "\n");
            }

            stringBuilder.append("If this cancellation was made in error or if you would like to place a new order, we’re here to help. Simply reply to this email or contact our customer service team.\n" +
                    "Thank you once again for choosing us. We look forward to serving you in the future!\n" +
                    "\n" +
                    "      Best regards, \n" +
                    "      Ekart Customer Service");
        }

        return stringBuilder.toString();
    }
}
