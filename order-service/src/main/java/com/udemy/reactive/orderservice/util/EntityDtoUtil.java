package com.udemy.reactive.orderservice.util;

import com.udemy.reactive.orderservice.dto.*;
import com.udemy.reactive.orderservice.entity.Order;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static void setTransactionRequestDto(RequestContext context) {
        TransactionRequestDto dto = new TransactionRequestDto();
        dto.setUserId(context.getOrderRequestDto().getUserId());
        dto.setAmount((int) Math.round(context.getProductDto().getPrice()));
        context.setTransactionRequestDto(dto);
    }

    public static Order getOrder(RequestContext context) {
        Order order = new Order();
        order.setProductId(context.getOrderRequestDto().getProductId());
        order.setUserId(context.getOrderRequestDto().getUserId());
        order.setAmount((int) Math.round(context.getProductDto().getPrice()));
        OrderStatus orderStatus = TransactionStatus.APPROVED
                .equals(context.getTransactionResponseDto().getStatus())
                ? OrderStatus.COMPLETED : OrderStatus.FAILED;
        order.setStatus(orderStatus);
        return order;
    }

    public static OrderResponseDto getOrderResponse(Order order) {
        OrderResponseDto responseDto = new OrderResponseDto();
        BeanUtils.copyProperties(order, responseDto);
        responseDto.setOrderId(order.getId());
        return responseDto;
    }
}
