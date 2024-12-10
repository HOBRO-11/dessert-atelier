package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.product.OptionQueryService;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionDto;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionSimpleDto;
import com.yangnjo.dessert_atelier.repository.product.query.OptionQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OptionQueryServiceImpl implements OptionQueryService {

    private final OptionQueryRepo optionQueryRepo;

    @Override
    public List<OptionSimpleDto> getAllSimpleByDpIdAndStatus(Long dpId, OptionStatus status, PageOption pageOption) {
        return optionQueryRepo.findAllSimpleByDpIdAndStatus(dpId, status, pageOption);
    }

    @Override
    public List<OptionSimpleDto> getAllSimpleByOptionIdIn(List<Long> optionIds) {
        return optionQueryRepo.findAllSimpleByOptionIdIn(optionIds);
    }

    @Override
    public Optional<OptionDto> getByOptionId(Long optionId) {
        return getByOptionId(optionId);
    }
}
