package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.dto.PresetTableDto;

public interface PresetTableQueryRepo {

  Optional<PresetTableDto> findByDpId(Long dpId);

  List<PresetTableDto> findAll();

}