package com.yangnjo.dessert_atelier.domain_service.delivery.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.delivery.DeliveryCompanyQueryService;
import com.yangnjo.dessert_atelier.domain_service.delivery.exception.DeliveryCompanyNotFoundException;
import com.yangnjo.dessert_atelier.repository.dto.DeliveryCompanyDto;
import com.yangnjo.dessert_atelier.repository.query.DeliveryCompanyQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DcQueryServiceImpl implements DeliveryCompanyQueryService {

  private final DeliveryCompanyQueryRepo deliveryCompanyQueryRepo;

  @Override
  public DeliveryCompanyDto getById(Long deliveryCompanyId) {
    return deliveryCompanyQueryRepo.find(deliveryCompanyId).orElseThrow(() -> new DeliveryCompanyNotFoundException());
  }

  @Override
  public List<DeliveryCompanyDto> getAll() {
    return deliveryCompanyQueryRepo.findAll();
  }
}
