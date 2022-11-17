package com.udemy.reactive.service;

import com.udemy.reactive.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {

        Flux.range(1,1000)
                .delayElements(Duration.ofSeconds(2))
                .map(num -> new ProductDto("Product-"+num, ThreadLocalRandom.current().nextInt(100,1000)))
                .flatMap(productDto -> this.productService.insertProduct(Mono.just(productDto)))
                .subscribe(System.out::println);
    }
}
