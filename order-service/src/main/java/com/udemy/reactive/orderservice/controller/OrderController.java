package com.udemy.reactive.orderservice.controller;

import com.udemy.reactive.orderservice.dto.OrderRequestDto;
import com.udemy.reactive.orderservice.dto.OrderResponseDto;
import com.udemy.reactive.orderservice.service.OrderFulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderFulfillmentService service;

    @PostMapping
    public Mono<OrderResponseDto> getOrder(@RequestBody Mono<OrderRequestDto> requestDtoMono) {
        return this.service.processOrder(requestDtoMono);
    }

    @GetMapping("/user/{id}")
    public Flux<OrderResponseDto> getOrdersForUsers(@PathVariable("id") Integer userId) {
        return this.service.getAllOrdersByUser(userId);
    }
}
