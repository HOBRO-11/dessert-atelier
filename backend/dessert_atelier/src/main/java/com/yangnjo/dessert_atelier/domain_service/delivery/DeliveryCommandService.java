package com.yangnjo.dessert_atelier.domain_service.delivery;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCreateDto;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCreateResult;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryUpdateResult;

public interface DeliveryCommandService {

    DeliveryCreateResult matchOrderAndDelivery(List<DeliveryCreateDto> dtos);

    DeliveryUpdateResult updateDeliveryStatus(List<DeliveryUpdateDto> dtos);

    void delete(String deliveryCode);

}