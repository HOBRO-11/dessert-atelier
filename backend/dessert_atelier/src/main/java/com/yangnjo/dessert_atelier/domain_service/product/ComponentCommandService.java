package com.yangnjo.dessert_atelier.domain_service.product;

import com.yangnjo.dessert_atelier.domain_service.product.dto.ComponentCreateDto;

public interface ComponentCommandService {

  Long createComponent(ComponentCreateDto dto);

  void updateComponentName(Long componentId, String name);

  /*
   * batch 전용 함수
   */
  void deleteComponent(Long componentId);

}