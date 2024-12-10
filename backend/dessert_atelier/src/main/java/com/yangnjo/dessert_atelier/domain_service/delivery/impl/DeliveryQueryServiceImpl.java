package com.yangnjo.dessert_atelier.domain_service.delivery.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.delivery.DeliveryQueryService;
import com.yangnjo.dessert_atelier.repository.delivery.dto.DeliveryDto;
import com.yangnjo.dessert_atelier.repository.delivery.query.DeliveryQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryQueryServiceImpl implements DeliveryQueryService {

    private final DeliveryQueryRepo deliveryQueryRepo;

    @Override
    public List<DeliveryDto> getAllByOrderCodeIn(List<Long> orderCodes) {
        return deliveryQueryRepo.findAllByOrderCodeIn(orderCodes);
    }

    @Override
    public Optional<DeliveryDto> getByDeliveryCode(String deliveryCode) {
        return deliveryQueryRepo.findById(deliveryCode);
    }

    @Override
    public Optional<DeliveryDto> getByOrdersOrderCode(Long orderCode) {
        return deliveryQueryRepo.findByOrderCode(orderCode);
    }

}
