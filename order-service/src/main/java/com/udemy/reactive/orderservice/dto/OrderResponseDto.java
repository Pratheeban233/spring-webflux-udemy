package com.udemy.reactive.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderResponseDto {
    private Integer userId;
    private String productId;
    private Integer orderId;
    private Integer amount;
    private OrderStatus status;
}
