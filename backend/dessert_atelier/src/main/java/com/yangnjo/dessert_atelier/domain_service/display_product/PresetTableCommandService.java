package com.yangnjo.dessert_atelier.domain_service.display_product;

import com.yangnjo.dessert_atelier.domain_service.display_product.dto.PresetTableCreateDto;

public interface PresetTableCommandService {

  Long createPresetTable(PresetTableCreateDto dto);

  void updatePresetTableNumbering(Long presetTableId, Integer numbering);

  void updatePresetTableDefault(Long presetTableId, Long defaultDppId);

  void updatePresetTableCurrent(Long presetTableId, Long currentDppId);

  /*
   * batch 전용 함수
   */
  void deletePresetTable(Long presetTableId);

}