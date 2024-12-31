package com.yangnjo.dessert_atelier.domain_service.total.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_service.total.TotalSaleOptionQueryService;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleOptionDto;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleOptionGraphDto;
import com.yangnjo.dessert_atelier.repository.total.query.TotalSaleOptionQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TsoQueryServiceImpl implements TotalSaleOptionQueryService {

    private final TotalSaleOptionQueryRepo totalSaleOptionQueryRepo;

    @Override
    public Page<TotalSaleOptionDto> findForPageByOptionId(Long optionId, PageOption pageOption,
            PeriodOption periodOption) {
        List<TotalSaleOptionDto> dtos = totalSaleOptionQueryRepo.findForPageByOptionId(optionId, pageOption,
                periodOption);
        return PageResponse.of(dtos, pageOption,
                () -> totalSaleOptionQueryRepo.countForPageByOptionId(optionId, periodOption));
    }

    @Override
    public List<TotalSaleOptionGraphDto> findForGraphByOptionId(Long optionId, PeriodOption periodOption) {
        return totalSaleOptionQueryRepo.findForGraphByOptionId(optionId, periodOption);
    }
}
