package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.display_product.QPresetTable.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PresetTableDto {

  private Long id;
  private Long dpId;
  private Long defaultDppId;
  private Long currentDppId;
  private Integer numbering;

  public static Expression<PresetTableDto> asDto() {
    return Projections.constructor(PresetTableDto.class,
        presetTable.id,
        presetTable.displayProduct.id,
        presetTable.defaultDpp.id,
        presetTable.currentDpp.id,
        presetTable.numbering);
  }
}
