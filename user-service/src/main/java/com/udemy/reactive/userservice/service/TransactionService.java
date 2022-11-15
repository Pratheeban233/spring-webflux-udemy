package com.udemy.reactive.userservice.service;

import com.udemy.reactive.userservice.dto.TransactionRequestDto;
import com.udemy.reactive.userservice.dto.TransactionResponseDto;
import com.udemy.reactive.userservice.dto.TransactionStatus;
import com.udemy.reactive.userservice.dto.UserTransactionDto;
import com.udemy.reactive.userservice.repo.UserRepository;
import com.udemy.reactive.userservice.repo.UserTransactionRepository;
import com.udemy.reactive.userservice.util.EntityToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository transactionRepository;


    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityToDto.toEntity(requestDto))
                .flatMap(this.transactionRepository::save)
                .map(ut -> EntityToDto.toDto(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityToDto.toDto(requestDto, TransactionStatus.DECLINED));

    }

    public Flux<UserTransactionDto> getAllTransactionsOfUser(Integer userId) {
        return this.transactionRepository.findAllByUserId(userId)
                .map(ut -> EntityToDto.toDto(ut));
    }
}
