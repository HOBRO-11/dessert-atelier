package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.repository.dto.DpDto;

public interface DpQueryRepo {

  List<DpDto> findAll(PageOption pageOption);

  Long countAll();

  List<DpDto> findBySaleStatusAndLikeNaming(SaleStatus saleStatus, String naming, PageOption pageOption);

  Long countBySaleStatusAndLikeNaming(SaleStatus saleStatus, String naming);

  List<DpDto> findByIds(List<Long> ids);

}