package com.udemy.reactive.util;

import com.udemy.reactive.dto.ProductDto;
import com.udemy.reactive.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static ProductDto toDto(Product product) {
        ProductDto productDTO = new ProductDto();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    public static Product toEntity(ProductDto productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}
