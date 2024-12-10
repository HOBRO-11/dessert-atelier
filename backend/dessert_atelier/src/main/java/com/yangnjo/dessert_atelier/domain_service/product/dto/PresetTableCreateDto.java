package com.yangnjo.dessert_atelier.domain_service.product.dto;

import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain_model.product.PresetTable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PresetTableCreateDto {
    Long dpId;
    Integer numbering;

    public PresetTable toEntity(DisplayProduct dp){
        return new PresetTable(dp);
    }
}
