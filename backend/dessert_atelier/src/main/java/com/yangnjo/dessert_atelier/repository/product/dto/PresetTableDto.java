package com.yangnjo.dessert_atelier.repository.product.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.product.QPresetTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PresetTableDto {

    private Long id;
    private Long dpId;
    private Integer numbering;

    public static Expression<PresetTableDto> asDto() {
        QPresetTable presetTable = QPresetTable.presetTable;
        return Projections.constructor(PresetTableDto.class,
                presetTable.id,
                presetTable.displayProduct.id,
                presetTable.numbering);
    }
}
