package com.yangnjo.dessert_atelier.domain_service.delivery.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.delivery.Delivery;
import com.yangnjo.dessert_atelier.domain.delivery.DeliveryCompany;
import com.yangnjo.dessert_atelier.domain.delivery.DeliveryStatus;
import com.yangnjo.dessert_atelier.domain_service.delivery.DeliveryCommandService;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCreateDto;
import com.yangnjo.dessert_atelier.domain_service.delivery.exception.DeliveryCompanyNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.delivery.exception.DeliveryNotFoundException;
import com.yangnjo.dessert_atelier.repository.DeliveryCompanyRepository;
import com.yangnjo.dessert_atelier.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class DeliveryCommandServiceImpl implements DeliveryCommandService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryCompanyRepository deliveryCompanyRepository;

    @Override
    public Long createDelivery(final DeliveryCreateDto dto) {
        DeliveryCompany company = findDeliveryCompanyById(dto.getDeliveryCompanyId());
        Delivery delivery = dto.toEntity(company);
        Delivery savedDelivery = deliveryRepository.save(delivery);
        return savedDelivery.getId();
    }

    @Override
    public void updateDeliveryStatus(Long deliveryId, DeliveryStatus status) {
        Delivery delivery = findDeliveryById(deliveryId);
        delivery.setDeliveryStatus(status);
    }

    @Override
    public void deleteDelivery(Long deliveryId) {
        deliveryRepository.deleteById(deliveryId);
    }

    @Override
    public void deleteDelivery(String deliveryCode, Long deliveryCompanyId) {
        Delivery delivery = deliveryRepository.findByDeliveryCodeAndDeliveryCompanyId(deliveryCode, deliveryCompanyId)
                .orElseThrow(() -> new DeliveryNotFoundException());
        deliveryRepository.deleteById(delivery.getId());
    }

    private DeliveryCompany findDeliveryCompanyById(Long deliveryCompanyId) {
        DeliveryCompany company = deliveryCompanyRepository.findById(deliveryCompanyId)
                .orElseThrow(() -> new DeliveryCompanyNotFoundException());
        return company;
    }

    private Delivery findDeliveryById(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException());
        return delivery;
    }
}
