package com.yangnjo.dessert_atelier.domain_service.product.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.model.ImageSrc;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplayProductCreateDto {
    String title;
    String thumb;
    String desc;
    List<ImageSrc> images;

    public DisplayProduct toEntity() {
        return new DisplayProduct(title, desc, thumb, images);
    }
}
