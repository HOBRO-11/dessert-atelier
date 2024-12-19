package com.yangnjo.dessert_atelier.service.product.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.model.ImageSrc;
import com.yangnjo.dessert_atelier.domain_service.product.dto.DisplayProductCreateDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DisplayProductEntityCreateForm {
    String title;
    String thumb;
    String desc;
    Integer optionLayer;
    List<String> imageNames;

    public DisplayProductCreateDto toDto(String path) {
        if (imageNames != null) {
            
            return new DisplayProductCreateDto(title, thumb, desc, optionLayer, ImageSrc.create(imageNames, path));
        }
        return new DisplayProductCreateDto(title, thumb, desc, optionLayer, null);
    }
}
