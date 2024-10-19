package com.yangnjo.dessert_atelier.domain_service.delivery.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.delivery.DeliveryCompany;
import com.yangnjo.dessert_atelier.domain_service.delivery.DeliveryCompanyCommandService;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCompanyCreateDto;
import com.yangnjo.dessert_atelier.domain_service.delivery.exception.DeliveryCompanyNotFoundException;
import com.yangnjo.dessert_atelier.repository.DeliveryCompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class DcCommandServiceImpl implements DeliveryCompanyCommandService {

  private final DeliveryCompanyRepository deliveryCompanyRepo;

  @Override
  public Long createDeliveryCompany(final DeliveryCompanyCreateDto dto) {
    DeliveryCompany deliveryCompany = deliveryCompanyRepo.save(dto.toEntity());
    return deliveryCompany.getId();
  }

  @Override
  public void updateDeliveryCompanyPhone(Long deliveryCompanyId, String phone) {
    DeliveryCompany deliveryCompany = findDeliveryCompanyById(deliveryCompanyId);
    deliveryCompany.setPhone(phone);
  }

  @Override
  public void deleteDeliveryCompany(Long deliveryCompanyId) {
    deliveryCompanyRepo.deleteById(deliveryCompanyId);
  }

  private DeliveryCompany findDeliveryCompanyById(Long deliveryCompanyId) {
    DeliveryCompany deliveryCompany = deliveryCompanyRepo.findById(deliveryCompanyId)
        .orElseThrow(() -> new DeliveryCompanyNotFoundException());
    return deliveryCompany;
  }

}
