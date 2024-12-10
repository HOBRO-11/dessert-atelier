package com.yangnjo.dessert_atelier.domain_service.delivery;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.delivery.dto.DeliveryDto;

public interface DeliveryQueryService {

    Optional<DeliveryDto> getByOrdersOrderCode(Long orderCode);

    Optional<DeliveryDto> getByDeliveryCode(String deliveryCode);

    List<DeliveryDto> getAllByOrderCodeIn(List<Long> orderCodes);
}