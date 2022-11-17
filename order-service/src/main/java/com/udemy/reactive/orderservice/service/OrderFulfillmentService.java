package com.udemy.reactive.orderservice.service;

import com.udemy.reactive.orderservice.client.ProductClient;
import com.udemy.reactive.orderservice.client.UserClient;
import com.udemy.reactive.orderservice.dto.OrderRequestDto;
import com.udemy.reactive.orderservice.dto.OrderResponseDto;
import com.udemy.reactive.orderservice.dto.RequestContext;
import com.udemy.reactive.orderservice.repo.OrderRepository;
import com.udemy.reactive.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulfillmentService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderRepository orderRepository;

    public Mono<OrderResponseDto> processOrder(Mono<OrderRequestDto> requestDtoMono) {
        return requestDtoMono
                .map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getOrder)
                .map(this.orderRepository::save) //blocking
                .map(EntityDtoUtil::getOrderResponse)
                .subscribeOn(Schedulers.boundedElastic()); // if any blocking code should run separate thread
        // otherwise will block main thread

    }

    private Mono<RequestContext> productRequestResponse(RequestContext context) {
        return this.productClient
                .getProductById(context.getOrderRequestDto().getProductId())
                .doOnNext(productDto -> System.out.println("productDto = " + productDto))
                .doOnNext(context::setProductDto)
                .thenReturn(context);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext context) {
        return this.userClient
                .authorizeTransaction(context.getTransactionRequestDto())
                .doOnNext(transactionResponseDto -> System.out.println("transactionResponseDto = " + transactionResponseDto))
                .doOnNext(context::setTransactionResponseDto)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(context);
    }

    public Flux<OrderResponseDto> getAllOrdersByUser(Integer userId) {
        return Flux.fromStream(() -> this.orderRepository.findByUserId(userId)
                        .stream().map(EntityDtoUtil::getOrderResponse))
                .subscribeOn(Schedulers.boundedElastic());
    }

}
