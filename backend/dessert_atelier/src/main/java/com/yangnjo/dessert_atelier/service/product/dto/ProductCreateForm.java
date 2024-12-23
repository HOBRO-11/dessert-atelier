package com.yangnjo.dessert_atelier.service.product.dto;

import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductCreateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductCreateForm {
    String name;
    Integer price;
    String thumb;
    Integer quantity;

    public ProductCreateDto toDto(){
        return new ProductCreateDto(name, price, thumb, quantity);
    }
}
