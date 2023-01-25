package com.rcosta.productservice.util;

import com.rcosta.productservice.dto.ProductDto;
import com.rcosta.productservice.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static ProductDto toDto(Product product){
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }
    public static Product toEntity(ProductDto dto){
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        return product;
    }
}
