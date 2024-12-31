package com.yangnjo.dessert_atelier.service.product.dto;

import org.springframework.web.multipart.MultipartFile;

import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ProductUpdateForm {
    @Setter
    Long productId;
    Integer price;
    String thumb;
    MultipartFile image;

    public ProductUpdateDto toDto(Integer price, String thumb) {
        return new ProductUpdateDto(productId, price, thumb);
    }
}
