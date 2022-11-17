package com.udemy.reactive.controller;

import com.udemy.reactive.dto.ProductDto;
import com.udemy.reactive.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("all")
    public Flux<ProductDto> getAllProducts() {
        return this.productService.getAllProducts();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable("id") String id) {
        this.simulateRandomException();
        return this.productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return this.productService.insertProduct(productDtoMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable("id") String id, @RequestBody Mono<ProductDto> productDtoMono) {
        return this.productService.updateProduct(id, productDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable("id") String id) {
        return this.productService.deleteProduct(id);
    }

    @GetMapping("price-range")
    public Flux<ProductDto> getProductBtwPriceRange(@RequestParam("min") double min, @RequestParam("max") double max) {
        return this.productService.getProductBtwPriceRange(min, max);
    }

    private void simulateRandomException() {
        int nextInt = ThreadLocalRandom.current().nextInt(1, 10);
        if(nextInt > 5)
            throw new RuntimeException("Something is wrong");
    }
}
