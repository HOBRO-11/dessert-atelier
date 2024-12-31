package com.yangnjo.dessert_atelier.domain_service.product.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplayProductCreateDto {
    String title;
    List<String> thumb;
    List<String> desc;
    Integer optionLayer;

    public DisplayProduct toEntity() {
        return new DisplayProduct(title, desc, thumb, optionLayer);
    }
}
