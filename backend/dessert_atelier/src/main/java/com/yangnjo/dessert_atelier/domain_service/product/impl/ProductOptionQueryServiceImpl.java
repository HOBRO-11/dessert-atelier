package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.product.ProductOptionQueryService;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionDto;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionSimpleDto;
import com.yangnjo.dessert_atelier.repository.product.query.ProductOptionQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOptionQueryServiceImpl implements ProductOptionQueryService {

    private final ProductOptionQueryRepo optionQueryRepo;

    @Override
    public boolean existsByOptionHeaderIdInAndStatus(List<Long> optionHeaderIds, OptionStatus status) {
        return optionQueryRepo.existsByOptionHeaderIdInAndStatus(optionHeaderIds, status);
    }

    @Override
    public List<ProductOptionSimpleDto> getAllSimpleByOptionIdIn(List<Long> optionIds) {
        return optionQueryRepo.findAllSimpleByOptionIdIn(optionIds);
    }

    @Override
    public List<ProductOptionSimpleDto> getAllSimpleByOptionHeaderAndStatus(Long optionHeaderId, OptionStatus status,
            PageOption pageOption) {
        return optionQueryRepo.findAllSimpleByOptionHeaderIdAndStatus(optionHeaderId, status, pageOption);
    }

    @Override
    public Optional<ProductOptionDto> getByOptionId(Long optionId) {
        return optionQueryRepo.findByOptionId(optionId);
    }
}
