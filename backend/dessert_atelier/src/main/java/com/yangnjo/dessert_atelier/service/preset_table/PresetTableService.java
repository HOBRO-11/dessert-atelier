package com.yangnjo.dessert_atelier.service.preset_table;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.product.dto.PresetTableDto;
import com.yangnjo.dessert_atelier.service.preset_table.dto.PresetTableUpdateForm;

public interface PresetTableService {

    List<PresetTableDto> getAllPresetTables();

    void update(List<PresetTableUpdateForm> forms);
}
