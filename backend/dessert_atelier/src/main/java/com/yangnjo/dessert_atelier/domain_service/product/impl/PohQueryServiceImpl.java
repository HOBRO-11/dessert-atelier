package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_service.product.ProductOptionHeaderQueryService;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionHeaderDto;
import com.yangnjo.dessert_atelier.repository.product.query.ProductOptionHeaderQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PohQueryServiceImpl implements ProductOptionHeaderQueryService {

    private final ProductOptionHeaderQueryRepo pohQueryRepo;

    @Override
    public Optional<ProductOptionHeaderDto> getById(Long optionHeaderId) {
        return pohQueryRepo.findById(optionHeaderId);
    }

    @Override
    public List<ProductOptionHeaderDto> getAllByOptionHeaderIdIn(List<Long> optionHeaderIds) {
        return pohQueryRepo.findAllByOptionHeaderIdIn(optionHeaderIds);
    }

    @Override
    public List<ProductOptionHeaderDto> findAllByDpId(Long displayProductId, PageOption pageOption) {
        return pohQueryRepo.findAllByDpId(displayProductId, pageOption);
    }

}
