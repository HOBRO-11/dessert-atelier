package com.yangnjo.dessert_atelier.service.product.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.domain_service.product.DisplayProductCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.DisplayProductQueryService;
import com.yangnjo.dessert_atelier.repository.product.dto.DpDto;
import com.yangnjo.dessert_atelier.repository.product.dto.DpSimpleDto;
import com.yangnjo.dessert_atelier.service.product.DisplayProductService;
import com.yangnjo.dessert_atelier.service.product.dto.DisplayProductEntityCreateForm;
import com.yangnjo.dessert_atelier.service.product.dto.DisplayProductEntityUpdateForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DisplayProductServiceImpl implements DisplayProductService {

    private final DisplayProductCommandService dpCommandService;
    private final DisplayProductQueryService dpQueryService;
    private static final String DP_PATH = "dp";

    @Override
    public Long create(DisplayProductEntityCreateForm form) {
        return dpCommandService.create(form.toDto(DP_PATH));
    }

    @Override
    public Page<DpSimpleDto> getAllSimpleByDpStatus(DisplayProductStatus displayProductStatus, PageOption pageOption) {
        return dpQueryService.getAllSimpleByDpStatus(displayProductStatus, pageOption);
    }

    @Override
    public Page<DpSimpleDto> getAllSimpleByExceptDpStatus(DisplayProductStatus displayProductStatus,
            PageOption pageOption) {
        return dpQueryService.getAllSimpleByExceptDpStatus(displayProductStatus, pageOption);
    }

    @Override
    public Optional<DpDto> getById(Long id) {
        return dpQueryService.getById(id);
    }

    @Override
    @Transactional
    public void update(DisplayProductEntityUpdateForm dto) {
        dpCommandService.update(dto.toDto(DP_PATH));
    }

    @Override
    @Transactional
    public void updateDisplayProductStatus(Long dpId, DisplayProductStatus saleStatus) {
        dpCommandService.updateDisplayProductStatus(dpId, saleStatus);
    }

}
