package com.udemy.reactive.orderservice.repo;

import com.udemy.reactive.orderservice.dto.OrderResponseDto;
import com.udemy.reactive.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
   List<Order> findByUserId(Integer userId);
}
