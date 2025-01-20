package com.yangnjo.dessert_atelier.statistics.domain.respository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.commons.util.page.PeriodOption;
import com.yangnjo.dessert_atelier.statistics.dto.TotalSaleProductDto;
import com.yangnjo.dessert_atelier.statistics.dto.TotalSaleProductGraphDto;

public interface TotalSaleProductQueryService {

    List<TotalSaleProductDto> findForPageByProductId(Long productId, PageOption pageOption, PeriodOption periodOption);

    Long countForPageByProductId(Long productId, PeriodOption periodOption);

    List<TotalSaleProductGraphDto> findForGraphByProductId(Long productId, PeriodOption periodOption);

}