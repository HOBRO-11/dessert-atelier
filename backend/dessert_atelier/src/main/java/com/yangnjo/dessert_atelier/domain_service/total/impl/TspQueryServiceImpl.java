package com.yangnjo.dessert_atelier.domain_service.total.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_service.total.TotalSaleProductQueryService;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleProductDto;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleProductGraphDto;
import com.yangnjo.dessert_atelier.repository.total.query.TotalSaleProductQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TspQueryServiceImpl implements TotalSaleProductQueryService {

    private final TotalSaleProductQueryRepo totalSaleProductQueryRepo;

    @Override
    public Page<TotalSaleProductDto> findForPageByOptionId(Long productId, PageOption pageOption,
            PeriodOption periodOption) {
        List<TotalSaleProductDto> dtos = totalSaleProductQueryRepo.findForPageByProductId(productId, pageOption,
                periodOption);
        return PageResponse.of(dtos, pageOption,
                () -> totalSaleProductQueryRepo.countForPageByProductId(productId, periodOption));
    }

    @Override
    public List<TotalSaleProductGraphDto> findForGraphByProductId(Long productId, PeriodOption periodOption) {
        return totalSaleProductQueryRepo.findForGraphByProductId(productId, periodOption);
    }
}
