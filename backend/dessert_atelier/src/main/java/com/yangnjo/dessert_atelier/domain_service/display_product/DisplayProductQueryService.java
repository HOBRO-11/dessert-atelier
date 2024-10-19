package com.yangnjo.dessert_atelier.domain_service.display_product;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.repository.dto.DpDto;

public interface DisplayProductQueryService {

  Page<DpDto> getAllDisplayProducts(PageOption pageOption);

  Page<DpDto> getDisplayProductsBySaleStatusAndNaming(SaleStatus saleStatus, String naming,
      PageOption pageOption);

  List<DpDto> getDisplayProductsByIds(List<Long> ids);

}