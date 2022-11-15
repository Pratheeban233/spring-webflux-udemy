package com.udemy.reactive.service;

import com.udemy.reactive.dto.ProductDto;
import com.udemy.reactive.repo.ProductRepository;
import com.udemy.reactive.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> getAllProducts() {
        return this.productRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return this.productRepository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.productRepository::insert)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto>     updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return this.productRepository.findById(id)
                .flatMap(p -> productDtoMono //p just checking the object is existing or not
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(this.productRepository::save)
                .map(EntityDtoUtil::toDto);

    }

    public Mono<Void> deleteProduct(String id) {
        return this.productRepository.deleteById(id);
    }

    public Flux<ProductDto> getProductBtwPriceRange(double min, double max) {
        return this.productRepository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }
}
