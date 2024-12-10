package com.yangnjo.dessert_atelier.domain_service.product;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.product.dto.PresetTableDto;

public interface PresetTableQueryService {

    List<PresetTableDto> getAllPresetTables();

}