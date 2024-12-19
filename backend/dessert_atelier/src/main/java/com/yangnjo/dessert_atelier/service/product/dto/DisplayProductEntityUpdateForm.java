package com.yangnjo.dessert_atelier.service.product.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.model.ImageSrc;
import com.yangnjo.dessert_atelier.domain_service.product.dto.DisplayProductUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplayProductEntityUpdateForm {
    Long dpId;
    String thumb;
    String desc;
    Integer optionLayer;
    List<String> imageNames;

    public DisplayProductUpdateDto toDto(String path) {
        if (thumb != null || imageNames != null) {
            return new DisplayProductUpdateDto(dpId, null, thumb, desc, optionLayer, ImageSrc.create(imageNames, path));
        }

        return new DisplayProductUpdateDto(dpId, null, thumb, desc, optionLayer, null);
    }
}
