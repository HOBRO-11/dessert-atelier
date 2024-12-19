package com.yangnjo.dessert_atelier.domain_service.product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain_model.product.PresetTable;
import com.yangnjo.dessert_atelier.domain_service.product.PresetTableCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.PresetTableCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.repository.product.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.product.PresetTableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class PtCommandServiceImpl implements PresetTableCommandService {

    private final PresetTableRepository presetTableRepository;
    private final DisplayProductRepository displayProductRepository;

    @Override
    public Long create(PresetTableCreateDto dto) {
        DisplayProduct dp = findDisplayProductById(dto.getDpId());
        PresetTable presetTable = presetTableRepository.save(dto.toEntity(dp));
        presetTable.setNumbering(dto.getNumbering());
        return presetTable.getId();
    }

    @Override
    public void updateNumbering(Long presetTableId, Integer numbering) {
        PresetTable pt = findPresetTableById(presetTableId);
        pt.setNumbering(numbering);
    }

    @Override
    public void delete(Long presetTableId) {
        presetTableRepository.deleteById(presetTableId);
    }

    private PresetTable findPresetTableById(Long presetTableId) {
        PresetTable presetTable = presetTableRepository.findById(presetTableId)
                .orElseThrow(() -> new DisplayProductNotFountException());
        return presetTable;
    }

    private DisplayProduct findDisplayProductById(Long dpId) {
        DisplayProduct displayProduct = displayProductRepository.findById(dpId)
                .orElseThrow(() -> new DisplayProductNotFountException());
        return displayProduct;
    }
}
