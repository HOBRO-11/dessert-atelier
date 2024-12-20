package com.yangnjo.dessert_atelier.service.total.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_service.total.TotalSaleProductQueryService;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleProductDto;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleProductGraphDto;
import com.yangnjo.dessert_atelier.service.total.TotalSaleProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TspServiceImpl implements TotalSaleProductService {

    private final TotalSaleProductQueryService tspQueryService;

    @Override
    public Page<TotalSaleProductDto> getByOptionId(Long productId, PageOption pageOption,
            PeriodOption periodOption) {
        return tspQueryService.findForPageByOptionId(productId, pageOption, periodOption);
    }

    @Override
    public List<TotalSaleProductGraphDto> getGraphByProductId(Long productId, PeriodOption periodOption) {
        return tspQueryService.findForGraphByProductId(productId, periodOption);
    }

}
