package com.yangnjo.dessert_atelier.service.total.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_service.total.TotalSaleOptionQueryService;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleOptionDto;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleOptionGraphDto;
import com.yangnjo.dessert_atelier.service.total.TotalSaleOptionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TsoServiceImpl implements TotalSaleOptionService{

    private final TotalSaleOptionQueryService tsoQueryService;

    @Override
    public Page<TotalSaleOptionDto> getByOptionId(Long optionId, PageOption pageOption,
            PeriodOption periodOption) {
        return tsoQueryService.findForPageByOptionId(optionId, pageOption, periodOption);
    }

    @Override
    public List<TotalSaleOptionGraphDto> getGraphByOptionId(Long optionId, PeriodOption periodOption) {
        return tsoQueryService.findForGraphByOptionId(optionId, periodOption);
    }
    
}
