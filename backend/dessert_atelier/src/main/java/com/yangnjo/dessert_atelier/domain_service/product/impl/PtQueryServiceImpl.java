package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.product.PresetTableQueryService;
import com.yangnjo.dessert_atelier.repository.product.dto.PresetTableDto;
import com.yangnjo.dessert_atelier.repository.product.query.PresetTableQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PtQueryServiceImpl implements PresetTableQueryService {

    private final PresetTableQueryRepo presetTableQueryRepo;

    @Override
    public List<PresetTableDto> getAllPresetTables() {
        return presetTableQueryRepo.findAll();
    }
}
