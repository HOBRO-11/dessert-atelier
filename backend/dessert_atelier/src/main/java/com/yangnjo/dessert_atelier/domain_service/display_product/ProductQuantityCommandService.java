package com.yangnjo.dessert_atelier.domain_service.display_product;

import com.yangnjo.dessert_atelier.domain_service.display_product.dto.ProductQuantityCreateDto;

public interface ProductQuantityCommandService {

  Long createProductQuantity(ProductQuantityCreateDto dto);

  /*
   * batch 전용 함수
   */
  void deleteProductQuantity(Long productQuantityId);

}