package com.yangnjo.dessert_atelier.domain_service.display_product.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.display_product.PresetTableQueryService;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.PresetTableNotFoundException;
import com.yangnjo.dessert_atelier.repository.dto.PresetTableDto;
import com.yangnjo.dessert_atelier.repository.query.PresetTableQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PtQueryServiceImpl implements PresetTableQueryService {

    private final PresetTableQueryRepo presetTableQueryRepo;

    @Override
    public PresetTableDto getPresetTableByDpId(Long dpId) {
        return presetTableQueryRepo.findByDpId(dpId).orElseThrow(() -> new PresetTableNotFoundException());
    }

    @Override
    public List<PresetTableDto> getAllPresetTables() {
        return presetTableQueryRepo.findAll();
    }
}
