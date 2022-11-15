package com.udemy.reactive.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestContext {
    private OrderRequestDto orderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    public RequestContext(OrderRequestDto orderRequestDto) {
        this.orderRequestDto = orderRequestDto;
    }
}
