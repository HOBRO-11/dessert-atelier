package com.yangnjo.dessert_atelier.service.product.dto;

import org.springframework.web.multipart.MultipartFile;

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
    MultipartFile image;

    public ProductCreateDto toDto(String thumb){
        return new ProductCreateDto(name, price, thumb, quantity);
    }
}
