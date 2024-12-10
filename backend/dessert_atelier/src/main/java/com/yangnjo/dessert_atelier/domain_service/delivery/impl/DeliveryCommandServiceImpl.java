package com.yangnjo.dessert_atelier.domain_service.delivery.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_model.delivery.Delivery;
import com.yangnjo.dessert_atelier.domain_model.order.Orders;
import com.yangnjo.dessert_atelier.domain_service.delivery.DeliveryCommandService;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCreateDto;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCreateResult;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCreateResult.DeliveryCreateErrorMessage;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryUpdateResult;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryUpdateResult.DeliveryUpdateErrorMessage;
import com.yangnjo.dessert_atelier.repository.delivery.DeliveryRepository;
import com.yangnjo.dessert_atelier.repository.order.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class DeliveryCommandServiceImpl implements DeliveryCommandService {

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    @Override
    public DeliveryCreateResult matchOrderAndDelivery(List<DeliveryCreateDto> dtos) {
        Map<Long, Orders> ordersMap = getOrdersMap(dtos);
        Map<Long, Delivery> deliveryMap = getDeliveryMapByOrderCode(dtos);

        List<DeliveryCreateErrorMessage> errors = new ArrayList<>();
        List<Delivery> deliveries = new ArrayList<>();

        int totalCount = dtos.size();
        int successCount = 0;
        int errorCount = 0;

        for (DeliveryCreateDto dto : dtos) {
            Long orderCode = dto.getOrderCode();

            Orders orders = ordersMap.get(orderCode);

            if (orders == null) {
                errors.add(DeliveryCreateResult.create(orderCode, "주문 정보가 존재하지 않습니다."));
                errorCount++;
                continue;
            }

            if (deliveryMap.get(orderCode) != null) {
                errors.add(DeliveryCreateResult.create(orderCode, "해당 주문에 대하여 이미 배달이 등록되어있습니다."));
                errorCount++;
                continue;
            }

            deliveries.add(dto.toEntity(orders));
            successCount++;
        }

        deliveryRepository.saveAll(deliveries);

        return new DeliveryCreateResult(totalCount, successCount, errorCount, errors);
    }

    @Override
    public DeliveryUpdateResult updateDeliveryStatus(List<DeliveryUpdateDto> dtos) {

        if (dtos == null || dtos.isEmpty()) {
            return new DeliveryUpdateResult(0, 0, 0, new ArrayList<>());
        }

        int totalCount = dtos.size();
        int successCount = 0;
        int errorCount = 0;

        List<DeliveryUpdateErrorMessage> errors = new ArrayList<>();

        for (DeliveryUpdateDto dto : dtos) {
            Delivery delivery = getDelivery(dto.getDeliveryCode());

            if (delivery == null) {
                errors.add(DeliveryUpdateResult.create(dto.getDeliveryCode(),
                        "주문 및 배송업체 정보가 존재하지 않습니다."));
                errorCount++;
                continue;
            }

            delivery.setDeliveryStatus(dto.getDeliveryStatus());
            successCount++;
        }

        return new DeliveryUpdateResult(totalCount, successCount, errorCount, errors);
    }

    @Override
    public void delete(String deliveryCode) {
        deliveryRepository.deleteById(deliveryCode);
    }

    private Delivery getDelivery(String deliveryCode) {
        Delivery delivery = deliveryRepository
                .findById(deliveryCode)
                .orElse(null);
        return delivery;
    }

    private Map<Long, Orders> getOrdersMap(List<DeliveryCreateDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new HashMap<>();
        }

        List<Long> orderCodes = dtos.stream().map(DeliveryCreateDto::getOrderCode).collect(Collectors.toList());
        Map<Long, Orders> ordersMap = orderRepository.findAllById(orderCodes).stream()
                .collect(Collectors.toMap(Orders::getOrderCode, t -> t));
        return ordersMap;
    }

    private Map<Long, Delivery> getDeliveryMapByOrderCode(List<DeliveryCreateDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new HashMap<>();
        }

        List<Long> orderCodes = dtos.stream().map(DeliveryCreateDto::getOrderCode).collect(Collectors.toList());
        Map<Long, Delivery> deliveryMap = deliveryRepository.findAllByOrdersOrderCodeIn(orderCodes).stream()
                .collect(Collectors.toMap(t -> t.getOrders().getOrderCode(), t -> t));
        return deliveryMap;
    }
}
