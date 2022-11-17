package com.udemy.reactive.orderservice;

import com.udemy.reactive.orderservice.client.ProductClient;
import com.udemy.reactive.orderservice.client.UserClient;
import com.udemy.reactive.orderservice.dto.OrderRequestDto;
import com.udemy.reactive.orderservice.dto.OrderResponseDto;
import com.udemy.reactive.orderservice.dto.ProductDto;
import com.udemy.reactive.orderservice.dto.UserDto;
import com.udemy.reactive.orderservice.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collection;
import java.util.List;

@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderFulfillmentService service;

    @Test
    void contextLoads() {
        Flux<OrderResponseDto> orderResponseDtoFlux = Flux.zip(productClient.getAllProducts(), userClient.getAllUsers())
                .map(t -> buildOrderRequest(t.getT1(), t.getT2()))
                .flatMap(orderRequestDto -> this.service.processOrder(Mono.just(orderRequestDto)))
                .doOnNext(System.out::println);

        StepVerifier.create(orderResponseDtoFlux)
                .expectNextCount(3)
                .verifyComplete();

    }

    private OrderRequestDto buildOrderRequest(ProductDto productDto, UserDto userDto) {
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setProductId(productDto.getId());
        requestDto.setUserId(userDto.getId());
        return requestDto;
    }

}
