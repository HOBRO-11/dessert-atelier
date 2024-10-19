package com.yangnjo.dessert_atelier.domain_service.product;

import com.yangnjo.dessert_atelier.domain.product.ProductStatus;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductUpdateDto;

public interface ProductCommandService {

  Long createProduct(ProductCreateDto dto);

  void updateProduct(ProductUpdateDto dto);

  void updateProductStatus(Long productId, ProductStatus status);

  /*
   * batch 전용 함수
   */
  void deleteProduct(Long productId);

}