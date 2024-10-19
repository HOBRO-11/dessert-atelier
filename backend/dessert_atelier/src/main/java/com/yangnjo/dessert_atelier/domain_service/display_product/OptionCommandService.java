package com.yangnjo.dessert_atelier.domain_service.display_product;

import com.yangnjo.dessert_atelier.domain.display_product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.OptionCreateDto;

public interface OptionCommandService {

  Long createOption(OptionCreateDto dto);

  void updateOptionStatus(Long optionId, OptionStatus status);

  void updateOptionTotalQuantity(Long optionId, Integer totalQuantity);

  /*
   * batch 전용 함수
   */
  void deleteOption(Long optionId);

}