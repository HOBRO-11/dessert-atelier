package com.yangnjo.dessert_atelier.service.product.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_service.product.dto.DisplayProductUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplayProductEntityUpdateForm {
    Long dpId;
    List<String> thumb;
    String desc;
    Integer optionLayer;

    public DisplayProductUpdateDto toDto(String path) {
        return new DisplayProductUpdateDto(dpId, null, thumb, desc, optionLayer);
    }
}
