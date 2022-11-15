package com.udemy.reactive.userservice.util;

import com.udemy.reactive.userservice.dto.*;
import com.udemy.reactive.userservice.entity.User;
import com.udemy.reactive.userservice.entity.UserTransaction;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityToDto {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public static UserTransaction toEntity(TransactionRequestDto transactionRequestDto) {
        UserTransaction ut = new UserTransaction();
        ut.setUserId(transactionRequestDto.getUserId());
        ut.setAmount(transactionRequestDto.getAmount());
        ut.setTransactionDate(LocalDateTime.now());
        return ut;
    }

    public static TransactionResponseDto toDto(TransactionRequestDto requestDto, TransactionStatus status) {
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setUserId(requestDto.getUserId());
        responseDto.setAmount(requestDto.getAmount());
        responseDto.setStatus(status);
        return responseDto;
    }

    public static UserTransactionDto toDto(UserTransaction userTransaction) {
        UserTransactionDto transactionDto = new UserTransactionDto();
        BeanUtils.copyProperties(userTransaction, transactionDto);
        return transactionDto;
    }
}
