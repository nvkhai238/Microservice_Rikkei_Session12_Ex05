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


@RestController
class OrderController {
 private final OrderRepository repository;
 OrderController(OrderRepository repository) { this.repository=repository; }
 @GetMapping("/api/orders/{id}") OrderView get(@PathVariable Long id) {
  OrderEntity order=repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
  return new OrderView(order.id,order.status);
 }
}
