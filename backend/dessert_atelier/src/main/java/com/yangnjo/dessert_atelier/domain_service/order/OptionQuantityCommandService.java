package com.yangnjo.dessert_atelier.domain_service.order;

import com.yangnjo.dessert_atelier.domain.order.OptionQuantityStatus;
import com.yangnjo.dessert_atelier.domain_service.order.dto.OptionQuantityCreateDto;

public interface OptionQuantityCommandService {

  Long createOptionQuantity(OptionQuantityCreateDto dto);

  void updateOptionQuantityStatus(Long oqId, OptionQuantityStatus status);

  /*
   * batch 전용 함수
   */
  void deleteOptionQuantity(Long oqId);

}