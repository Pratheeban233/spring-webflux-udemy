package com.udemy.reactive.orderservice.controller;

import com.udemy.reactive.orderservice.dto.OrderRequestDto;
import com.udemy.reactive.orderservice.dto.OrderResponseDto;
import com.udemy.reactive.orderservice.service.OrderFulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderFulfillmentService service;

    @PostMapping
    public Mono<ResponseEntity<OrderResponseDto>> getOrder(@RequestBody Mono<OrderRequestDto> requestDtoMono) {
        return this.service.processOrder(requestDtoMono)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("/user/{id}")
    public Flux<OrderResponseDto> getOrdersForUsers(@PathVariable("id") Integer userId) {
        return this.service.getAllOrdersByUser(userId);
    }
}
