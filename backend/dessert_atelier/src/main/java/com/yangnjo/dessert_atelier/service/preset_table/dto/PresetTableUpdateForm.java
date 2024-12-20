package com.yangnjo.dessert_atelier.service.preset_table.dto;

import com.yangnjo.dessert_atelier.domain_service.product.dto.PresetTableCreateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PresetTableUpdateForm {
    private Long dpId;
    private Integer numbering;

    public PresetTableCreateDto toDto(){
        return new PresetTableCreateDto(dpId, numbering);
    }

}
