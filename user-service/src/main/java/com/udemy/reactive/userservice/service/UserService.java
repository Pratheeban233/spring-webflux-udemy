package com.udemy.reactive.userservice.service;

import com.udemy.reactive.userservice.dto.UserDto;
import com.udemy.reactive.userservice.repo.UserRepository;
import com.udemy.reactive.userservice.util.EntityToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Flux<UserDto> getAllUsers() {
        return userRepository.findAll().map(EntityToDto::toDto);
    }

    public Mono<UserDto> getUser(Integer userId) {
        return userRepository.findById(userId).map(EntityToDto::toDto);
    }

    public Mono<UserDto> createUser(Mono<UserDto> userDtoMono) {
        return userDtoMono.map(EntityToDto::toEntity)
                .flatMap(this.userRepository::save)
                .map(EntityToDto::toDto);
    }

    public Mono<UserDto> updateUser(Integer userId, Mono<UserDto> userDtoMono) {
        return this.userRepository.findById(userId)
                .flatMap(user -> userDtoMono.map(EntityToDto::toEntity)
                        .doOnNext(u -> u.setId(userId)))
                .flatMap(this.userRepository::save)
                .map(EntityToDto::toDto);
    }

    public Mono<Void> deleteUser(Integer userId) {
        return this.userRepository.deleteById(userId);
    }
}
