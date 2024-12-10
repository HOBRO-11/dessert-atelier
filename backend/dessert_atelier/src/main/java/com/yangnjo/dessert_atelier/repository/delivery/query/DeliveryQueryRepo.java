package com.yangnjo.dessert_atelier.repository.delivery.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.delivery.dto.DeliveryDto;

public interface DeliveryQueryRepo {

    Optional<DeliveryDto> findByOrderCode(Long orderCode);

    Optional<DeliveryDto> findById(String deliveryCode); 

    List<DeliveryDto> findAllByOrderCodeIn(List<Long> orderCodes);

}