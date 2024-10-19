package com.yangnjo.dessert_atelier.repository.query;

import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.dto.DeliveryDto;

public interface DeliveryQueryRepo {

  Optional<DeliveryDto> findByDeliveryId(Long deliveryId);

}