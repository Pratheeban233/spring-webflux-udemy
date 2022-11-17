package com.udemy.reactive.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ProductDto {

    private String id;
    private String description;
    private int price;

    public ProductDto(String description, int price) {
        this.description = description;
        this.price = price;
    }
}
