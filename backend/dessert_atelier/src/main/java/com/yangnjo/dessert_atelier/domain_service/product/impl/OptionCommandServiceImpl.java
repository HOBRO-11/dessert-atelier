package com.yangnjo.dessert_atelier.domain_service.product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain_model.product.Option;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.product.OptionCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.OptionCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.domain_service.product.exception.OptionNotFoundException;
import com.yangnjo.dessert_atelier.repository.product.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.product.OptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class OptionCommandServiceImpl implements OptionCommandService {

    private final OptionRepository optionRepository;
    private final DisplayProductRepository dpRepository;

    @Override
    public Long create(OptionCreateDto dto) {
        DisplayProduct dp = findDpById(dto);
        return optionRepository.save(dto.toEntity(dp)).getId();
    }

    @Override
    public void updateOptionStatus(Long optionId, OptionStatus status) {
        Option option = findOptionById(optionId);
        option.setOptionStatus(status);
    }

    private DisplayProduct findDpById(OptionCreateDto dto) {
        return dpRepository.findById(dto.getDpId()).orElseThrow(DisplayProductNotFountException::new);
    }

    private Option findOptionById(Long optionId) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionNotFoundException());
        return option;
    }
}
