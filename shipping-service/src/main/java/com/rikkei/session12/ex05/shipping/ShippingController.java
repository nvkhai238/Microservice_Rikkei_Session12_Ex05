package com.rikkei.session12.ex05.shipping;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/shipping")
class ShippingController {
 private final KafkaTemplate<String,String> kafka;
 private final ObjectMapper mapper;
 ShippingController(KafkaTemplate<String,String> kafka,ObjectMapper mapper) { this.kafka=kafka;this.mapper=mapper; }
 @PostMapping("/status") @ResponseStatus(HttpStatus.ACCEPTED)
 ShippingStatusEvent update(@Valid @RequestBody ShippingRequest request) throws Exception {
  ShippingStatusEvent event=new ShippingStatusEvent(request.orderId(),request.status(),request.customerEmail());
  kafka.send("shipping-status-events",request.orderId().toString(),mapper.writeValueAsString(event));
  return event;
 }
}
