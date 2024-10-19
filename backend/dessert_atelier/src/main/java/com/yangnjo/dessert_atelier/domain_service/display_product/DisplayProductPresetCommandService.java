package com.yangnjo.dessert_atelier.domain_service.display_product;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DppCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DppUpdateDto;

public interface DisplayProductPresetCommandService {

  Long createDefaultDPP(DppCreateDto dto);

  Long createCustomDPP(DppCreateDto dto);

  void updateDPP(DppUpdateDto dto);

  void updateDppDefault(Long dppId);

  void updateDppOptionLayer(Long dppId, Integer optionLayer);

  void updateDppDateTime(Long dppId, LocalDateTime startDateTime, LocalDateTime endDateTime);

  /*
   * batch 전용 함수
   */
  void deleteDisplayProductPreset(Long dppId);

}