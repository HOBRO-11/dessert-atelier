package com.yangnjo.dessert_atelier.repository.total.query;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleOptionDto;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleOptionGraphDto;

public interface TotalSaleOptionQueryRepo {

    List<TotalSaleOptionDto> findForPageByOptionId(Long optionId, PageOption pageOption, PeriodOption periodOption);

    Long countForPageByOptionId(Long optionId, PeriodOption periodOption);

    List<TotalSaleOptionGraphDto> findForGraphByOptionId(Long optionId, PeriodOption periodOption);

}