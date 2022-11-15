package com.udemy.reactive.orderservice.client;

import com.udemy.reactive.orderservice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${product.service.url}") String productUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(productUrl)
                .build();
    }

    public Mono<ProductDto> getProductById(final String productId) {
        return this.webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }
}
