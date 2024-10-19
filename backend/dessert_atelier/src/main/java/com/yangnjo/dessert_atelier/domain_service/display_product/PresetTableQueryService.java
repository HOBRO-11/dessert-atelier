package com.yangnjo.dessert_atelier.domain_service.display_product;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.PresetTableDto;

public interface PresetTableQueryService {

  PresetTableDto getPresetTableByDpId(Long dpId);

  List<PresetTableDto> getAllPresetTables();

}