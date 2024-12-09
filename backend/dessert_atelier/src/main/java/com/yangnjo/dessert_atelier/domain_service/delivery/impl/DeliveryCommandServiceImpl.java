package com.yangnjo.dessert_atelier.domain_service.delivery.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.delivery.Delivery;
import com.yangnjo.dessert_atelier.domain.delivery.DeliveryCompany;
import com.yangnjo.dessert_atelier.domain.order.Orders;
import com.yangnjo.dessert_atelier.domain_service.delivery.DeliveryCommandService;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCreateDto;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCreateResult;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCreateResult.DeliveryCreateErrorMessage;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryUpdateResult;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryUpdateResult.DeliveryUpdateErrorMessage;
import com.yangnjo.dessert_atelier.domain_service.delivery.exception.DeliveryNotFoundException;
import com.yangnjo.dessert_atelier.repository.DeliveryCompanyRepository;
import com.yangnjo.dessert_atelier.repository.DeliveryRepository;
import com.yangnjo.dessert_atelier.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class DeliveryCommandServiceImpl implements DeliveryCommandService {

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryCompanyRepository deliveryCompanyRepository;

    @Override
    public DeliveryCreateResult createDeliveries(List<DeliveryCreateDto> dtos) {
        Map<Long, Orders> ordersMap = getOrdersMap(dtos);
        Map<Long, DeliveryCompany> dcMap = getDcMap(dtos);
        Map<Long, Delivery> deliveryMap = getDeliveryMapByOrderCode(dtos);

        List<DeliveryCreateErrorMessage> errors = new ArrayList<>();
        List<Delivery> deliveries = new ArrayList<>();

        int totalCount = dtos.size();
        int successCount = 0;
        int errorCount = 0;

        for (DeliveryCreateDto dto : dtos) {
            Long orderCode = dto.getOrderCode();
            Long deliveryCompanyId = dto.getDeliveryCompanyId();

            Orders orders = ordersMap.get(orderCode);
            DeliveryCompany dc = dcMap.get(deliveryCompanyId);

            if (orders == null && dc == null) {
                errors.add(DeliveryCreateResult.create(orderCode, deliveryCompanyId, "주문 및 배송업체 정보가 존재하지 않습니다."));
                errorCount++;
                continue;
            }

            if (orders == null) {
                errors.add(DeliveryCreateResult.create(orderCode, deliveryCompanyId, "주문 정보가 존재하지 않습니다."));
                errorCount++;
                continue;
            }

            if (dc == null) {
                errors.add(DeliveryCreateResult.create(orderCode, deliveryCompanyId, "배송업체 정보가 존재하지 않습니다."));
                errorCount++;
                continue;
            }

            if (deliveryMap.get(orderCode) != null) {
                errors.add(DeliveryCreateResult.create(orderCode, deliveryCompanyId, "해당 주문에 대하여 이미 배달이 등록되어있습니다."));
                errorCount++;
                continue;
            }

            deliveries.add(dto.toEntity(orders, dc));
            successCount++;
        }

        deliveryRepository.saveAll(deliveries);

        return new DeliveryCreateResult(totalCount, successCount, errorCount, errors);
    }

    @Override
    public DeliveryUpdateResult updateDeliveries(List<DeliveryUpdateDto> dtos) {

        if (dtos == null || dtos.isEmpty()) {
            return new DeliveryUpdateResult(0, 0, 0, new ArrayList<>());
        }

        int totalCount = dtos.size();
        int successCount = 0;
        int errorCount = 0;

        List<DeliveryUpdateErrorMessage> errors = new ArrayList<>();

        for (DeliveryUpdateDto dto : dtos) {
            Delivery delivery = getDelivery(dto.getDeliveryCode(), dto.getDeliveryCompanyId());

            if (delivery == null) {
                errors.add(DeliveryUpdateResult.create(dto.getDeliveryCode(), dto.getDeliveryCompanyId(),
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
    public void deleteDelivery(String deliveryCode, Long deliveryCompanyId) {
        Delivery delivery = deliveryRepository.findByDeliveryCodeAndDeliveryCompanyId(deliveryCode, deliveryCompanyId)
                .orElseThrow(() -> new DeliveryNotFoundException());
        deliveryRepository.deleteById(delivery.getId());
    }

    private Delivery getDelivery(String deliveryCode, Long deliveryCompanyId) {
        Delivery delivery = deliveryRepository
                .findByDeliveryCodeAndDeliveryCompanyId(deliveryCode, deliveryCompanyId)
                .orElse(null);
        return delivery;
    }

    private Map<Long, DeliveryCompany> getDcMap(List<DeliveryCreateDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new HashMap<>();
        }

        Set<Long> dcIds = dtos.stream().map(DeliveryCreateDto::getDeliveryCompanyId).collect(Collectors.toSet());
        Map<Long, DeliveryCompany> dcMap = deliveryCompanyRepository.findAllById(dcIds).stream()
                .collect(Collectors.toMap(DeliveryCompany::getId, t -> t));
        return dcMap;
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
