package com.yangnjo.dessert_atelier.domain_service.display_product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.PresetTable;
import com.yangnjo.dessert_atelier.domain_service.display_product.PresetTableCommandService;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.PresetTableCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.PresetTableAlreadyException;
import com.yangnjo.dessert_atelier.repository.DisplayProductPresetRepository;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.PresetTableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class PtCommandServiceImpl implements PresetTableCommandService {

    private final PresetTableRepository presetTableRepository;
    private final DisplayProductRepository displayProductRepository;
    private final DisplayProductPresetRepository dppRepository;

    @Override
    public Long createPresetTable(final PresetTableCreateDto dto) {
        DisplayProduct displayProduct = findDisplayProductById(dto.getDpId());
        DisplayProductPreset dpp = findDppById(dto.getDppId());
        checkNonExitsPresetTable(displayProduct);
        PresetTable presetTable = new PresetTable(displayProduct, dpp);
        PresetTable savedPresetTable = presetTableRepository.save(presetTable);
        return savedPresetTable.getId();
    }

    @Override
    public void updatePresetTableNumbering(Long presetTableId, Integer numbering) {
        PresetTable presetTable = findPresetTableById(presetTableId);
        presetTable.setNumbering(numbering);
    }

    @Override
    public void updatePresetTableDefault(Long presetTableId, Long defaultDppId) {
        PresetTable presetTable = findPresetTableById(presetTableId);
        DisplayProductPreset dpp = findDppById(defaultDppId);
        presetTable.setDefaultDpp(dpp);
    }

    @Override
    public void updatePresetTableCurrent(Long presetTableId, Long currentDppId) {
        PresetTable presetTable = findPresetTableById(presetTableId);
        DisplayProductPreset dpp = findDppById(currentDppId);
        presetTable.setCurrentDpp(dpp);
    }

    /*
     * batch 전용 함수
     */
    @Override
    public void deletePresetTable(Long presetTableId) {
        presetTableRepository.deleteById(presetTableId);
    }

    private PresetTable findPresetTableById(Long presetTableId) {
        PresetTable presetTable = presetTableRepository.findById(presetTableId)
                .orElseThrow(() -> new DisplayProductNotFountException());
        return presetTable;
    }

    private DisplayProductPreset findDppById(Long defaultDppId) {
        return dppRepository.findById(defaultDppId).orElseThrow(() -> new DisplayProductNotFountException());
    }

    private DisplayProduct findDisplayProductById(Long dpId) {
        DisplayProduct displayProduct = displayProductRepository.findById(dpId)
                .orElseThrow(() -> new DisplayProductNotFountException());
        return displayProduct;
    }

    private void checkNonExitsPresetTable(DisplayProduct displayProduct) {
        boolean isExist = presetTableRepository.existsByDisplayProduct(displayProduct);
        if (isExist) {
            throw new PresetTableAlreadyException();
        }
    }
}
