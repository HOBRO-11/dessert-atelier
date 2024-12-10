package com.yangnjo.dessert_atelier.repository.product.query;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.product.dto.PresetTableDto;

public interface PresetTableQueryRepo {

    List<PresetTableDto> findAll();

}