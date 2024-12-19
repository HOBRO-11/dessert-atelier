package com.yangnjo.dessert_atelier.service.option.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.product.OptionCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.OptionQueryService;
import com.yangnjo.dessert_atelier.domain_service.product.ProductQuantityCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.PQCreateResult;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductQuantityCreateDto;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionDto;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionSimpleDto;
import com.yangnjo.dessert_atelier.service.option.OptionCreateForm;
import com.yangnjo.dessert_atelier.service.option.OptionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OptionServiceImpl implements OptionService{

    private final OptionQueryService optionQueryService;
    private final OptionCommandService optionCommandService;
    private final ProductQuantityCommandService productQuantityCommandService;

    @Override
    public List<OptionSimpleDto> getAllSimpleByDpIdAndStatus(Long dpId, OptionStatus status, PageOption pageOption) {
        return optionQueryService.getAllSimpleByDpIdAndStatus(dpId, status, pageOption);
    }

    @Override
    public Optional<OptionDto> getByOptionId(Long optionId) {
        return optionQueryService.getByOptionId(optionId);
    }

    @Override
    public List<OptionSimpleDto> getAllSimpleByOptionIdIn(List<Long> optionIds) {
        return optionQueryService.getAllSimpleByOptionIdIn(optionIds);
    }

    @Override
    @Transactional
    public PQCreateResult create(List<OptionCreateForm> form) {
        PQCreateResult pqCreateResult = null;

        for(OptionCreateForm ocf : form) {
            Long optionId = optionCommandService.create(ocf.toDto());
            List<ProductQuantityCreateDto> productQuantityDtos = ocf.toProductQuantityDtos(optionId);
            PQCreateResult result = productQuantityCommandService.create(productQuantityDtos);
            if(pqCreateResult == null) {
                pqCreateResult = result;
            } else {
                pqCreateResult.addCreateResult(result);
            }
        }
        
        if(pqCreateResult == null) {
            pqCreateResult = new PQCreateResult(0, 0, 0, null);
        }
        return pqCreateResult;
    }

    @Override
    @Transactional
    public void updateOptionStatus(Long optionId, OptionStatus status) {
        optionCommandService.updateOptionStatus(optionId, status);
    }
    
}
