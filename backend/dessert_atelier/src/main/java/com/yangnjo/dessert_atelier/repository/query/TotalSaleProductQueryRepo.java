package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.dto.TotalSaleProductDto;
import com.yangnjo.dessert_atelier.repository.dto.TotalSaleProductGraphDto;

public interface TotalSaleProductQueryRepo {

  List<TotalSaleProductDto> findForPageByProductId(Long productId, PageOption pageOption, PeriodOption periodOption);

  Long countForPageByProductId(Long productId, PeriodOption periodOption);

  List<TotalSaleProductGraphDto> findForGraphByProductId(Long productId, PeriodOption periodOption);

}