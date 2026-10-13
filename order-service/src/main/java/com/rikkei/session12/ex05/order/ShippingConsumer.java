package com.rikkei.session12.ex05.order;

import jakarta.persistence.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Service
class ShippingConsumer {
 private final OrderRepository repository;
 private final ObjectMapper mapper;
 ShippingConsumer(OrderRepository repository,ObjectMapper mapper) { this.repository=repository;this.mapper=mapper; }
 @KafkaListener(topics="shipping-status-events",groupId="order-service")
 @Transactional public void consume(String json) throws Exception {
  ShippingStatusEvent event=mapper.readValue(json,ShippingStatusEvent.class);
  if(!"DELIVERED".equals(event.status())) return;
  OrderEntity order=repository.findById(event.orderId()).orElseThrow(()->new IllegalStateException("Khong tim thay don hang "+event.orderId()));
  order.status="COMPLETED";
 }
}
