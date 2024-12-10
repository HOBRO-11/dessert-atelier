package com.yangnjo.dessert_atelier.domain_service.product;

import com.yangnjo.dessert_atelier.domain_service.product.dto.PresetTableCreateDto;

public interface PresetTableCommandService {

    Long create(PresetTableCreateDto dto);

    void updateNumbering(Long presetTableId, Integer numbering);

    void delete(Long presetTableId);

}