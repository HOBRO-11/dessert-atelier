package com.yangnjo.dessert_atelier.domain_service.display_product;

import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DisplayProductCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DisplayProductUpdateDto;

public interface DisplayProductCommandService {

  Long createDisplayProduct(DisplayProductCreateDto dto);

  void updateDisplayProduct(DisplayProductUpdateDto dto);

  void updateDisplayProductSaleStatus(Long dpId, SaleStatus saleStatus);

  /*
   * batch 전용 함수
   */
  void deleteDisplayProduct(Long dpId);

}