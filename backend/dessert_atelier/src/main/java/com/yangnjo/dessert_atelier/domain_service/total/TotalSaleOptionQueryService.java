package com.yangnjo.dessert_atelier.domain_service.total;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.dto.TotalSaleOptionDto;
import com.yangnjo.dessert_atelier.repository.dto.TotalSaleOptionGraphDto;

public interface TotalSaleOptionQueryService {

  Page<TotalSaleOptionDto> findForPageByOptionId(Long optionId, PageOption pageOption,
      PeriodOption periodOption);

  List<TotalSaleOptionGraphDto> findForGraphByOptionId(Long optionId, PeriodOption periodOption);

}