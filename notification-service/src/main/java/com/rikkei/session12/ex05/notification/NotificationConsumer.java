package com.rikkei.session12.ex05.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
class NotificationConsumer {
 private final ObjectMapper mapper;
 private final JavaMailSender mail;
 NotificationConsumer(ObjectMapper mapper,JavaMailSender mail) { this.mapper=mapper;this.mail=mail; }
 @KafkaListener(topics="shipping-status-events",groupId="notification-service")
 public void consume(String json) throws Exception {
  ShippingStatusEvent event=mapper.readValue(json,ShippingStatusEvent.class);
  if(!"DELIVERED".equals(event.status())) return;
  SimpleMailMessage message=new SimpleMailMessage();
  message.setFrom("shop@example.com");message.setTo(event.customerEmail());
  message.setSubject("Don hang "+event.orderId()+" da giao thanh cong");
  message.setText("Chuc mung ban da nhan duoc don hang. Cam on ban!");
  mail.send(message);
 }
}
