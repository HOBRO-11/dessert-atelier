package com.yangnjo.dessert_atelier.statistics.domain.respository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.commons.util.page.PeriodOption;
import com.yangnjo.dessert_atelier.statistics.dto.TotalSaleOptionDto;
import com.yangnjo.dessert_atelier.statistics.dto.TotalSaleOptionGraphDto;

public interface TotalSaleOptionQueryService {

    List<TotalSaleOptionDto> findForPageByOptionId(Long optionId, PageOption pageOption, PeriodOption periodOption);

    Long countForPageByOptionId(Long optionId, PeriodOption periodOption);

    List<TotalSaleOptionGraphDto> findForGraphByOptionId(Long optionId, PeriodOption periodOption);

}