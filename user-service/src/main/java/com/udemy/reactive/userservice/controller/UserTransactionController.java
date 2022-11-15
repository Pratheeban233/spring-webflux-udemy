package com.udemy.reactive.userservice.controller;

import com.udemy.reactive.userservice.dto.TransactionRequestDto;
import com.udemy.reactive.userservice.dto.TransactionResponseDto;
import com.udemy.reactive.userservice.dto.UserTransactionDto;
import com.udemy.reactive.userservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono) {
        return requestDtoMono.flatMap(this.transactionService::createTransaction);
    }

    @GetMapping("{id}")
    public Flux<UserTransactionDto> getUserTransactions(@PathVariable("id") Integer userId) {
        return this.transactionService.getAllTransactionsOfUser(userId);
    }
}
