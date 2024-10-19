package com.yangnjo.dessert_atelier.domain_service.display_product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.Option;
import com.yangnjo.dessert_atelier.domain.display_product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.OptionCommandService;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.OptionCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductPresetNotFountException;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.OptionNotFoundException;
import com.yangnjo.dessert_atelier.repository.DisplayProductPresetRepository;
import com.yangnjo.dessert_atelier.repository.OptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class OptionCommandServiceImpl implements OptionCommandService {

    private final OptionRepository optionRepository;
    private final DisplayProductPresetRepository displayProductPresetRepository;

    @Override
    public Long createOption(final OptionCreateDto dto) {
        DisplayProductPreset dpp = findDppById(dto.getDppId());
        Option option = dto.toEntity(dpp);
        Option savedOption = optionRepository.save(option);
        return savedOption.getId();
    }

    @Override
    public void updateOptionStatus(Long optionId, OptionStatus status) {
        Option option = findOptionById(optionId);
        option.setOptionStatus(status);
    }

    @Override
    public void updateOptionTotalQuantity(Long optionId, Integer totalQuantity) {
        Option option = findOptionById(optionId);
        option.setTotalQuantity(totalQuantity);
    }

    /*
     * batch 전용 함수
     */
    @Override
    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }

    private Option findOptionById(Long optionId) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionNotFoundException());
        return option;
    }

    private DisplayProductPreset findDppById(Long dppId) {
        DisplayProductPreset dpp = displayProductPresetRepository.findById(dppId)
                .orElseThrow(() -> new DisplayProductPresetNotFountException());
        return dpp;
    }
}
