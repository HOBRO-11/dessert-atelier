package com.yangnjo.dessert_atelier.domain_service.delivery.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.delivery.DeliveryQueryService;
import com.yangnjo.dessert_atelier.domain_service.delivery.exception.DeliveryNotFoundException;
import com.yangnjo.dessert_atelier.repository.dto.DeliveryDto;
import com.yangnjo.dessert_atelier.repository.query.DeliveryQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryQueryServiceImpl implements DeliveryQueryService {

  private final DeliveryQueryRepo deliveryQueryRepo;

  @Override
  public DeliveryDto getByCode(Long deliveryId) {
    return deliveryQueryRepo.findByDeliveryId(deliveryId).orElseThrow(() -> new DeliveryNotFoundException());
  }
}
