package com.yangnjo.dessert_atelier.domain_service.total;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.dto.TotalSaleProductDto;
import com.yangnjo.dessert_atelier.repository.dto.TotalSaleProductGraphDto;

public interface TotalSaleProductQueryService {

  Page<TotalSaleProductDto> findForPageByOptionId(Long productId, PageOption pageOption,
      PeriodOption periodOption);

  List<TotalSaleProductGraphDto> findForGraphByProductId(Long productId, PeriodOption periodOption);

}