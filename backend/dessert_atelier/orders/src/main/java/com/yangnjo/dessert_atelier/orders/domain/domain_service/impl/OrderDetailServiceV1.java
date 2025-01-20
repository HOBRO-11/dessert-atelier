package com.yangnjo.dessert_atelier.orders.domain.domain_service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.commons.value_type.Address;
import com.yangnjo.dessert_atelier.orders.domain.domain_service.OrderDetailService;
import com.yangnjo.dessert_atelier.orders.domain.entity.OrderDetail;
import com.yangnjo.dessert_atelier.orders.domain.entity.OrderStatus;
import com.yangnjo.dessert_atelier.orders.domain.repostiory.OrderDetailRepository;
import com.yangnjo.dessert_atelier.orders.dto.OrderCreateDto;
import com.yangnjo.dessert_atelier.orders.exception.OrderDetailCheckSumNotEnoughException;
import com.yangnjo.dessert_atelier.orders.exception.OrderDetailNotFoundException;
import com.yangnjo.dessert_atelier.orders.exception.OrderIdGenerateFailedException;
import com.yangnjo.dessert_atelier.orders.properties.OrderProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class OrderDetailServiceV1 implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepo;
    private final OrderProperties orderProperties;

    @Override
    public Long create(final OrderCreateDto dto) {
        Long orderId = generateOrderId();
        OrderDetail order = dto.toEntity(orderId);
        return orderDetailRepo.save(order).getId();
    }

    @Override
    public void minusCheckSum(Long orderId, Integer amountPrice) {
        OrderDetail orderDetail = findOrderByCode(orderId);
        Integer checkSum = orderDetail.getCheckSum();

        if (checkSum < amountPrice) {
            throw new OrderDetailCheckSumNotEnoughException(String.valueOf(checkSum));
        }

        orderDetail.setCheckSum(checkSum - amountPrice);
    }

    @Override
    public void setDeliveryCode(Long orderId, String deliveryCode) {
        findOrderByCode(orderId).setDeliveryCode(deliveryCode);
    }

    @Override
    public void updateAddress(Long orderId, Address address) {
        findOrderByCode(orderId).setAddress(address);
    }

    @Override
    public void updateStatus(Long orderId, OrderStatus status) {
        findOrderByCode(orderId).setStatus(status);
    }

    private OrderDetail findOrderByCode(Long orderId) {
        OrderDetail order = orderDetailRepo.findById(orderId)
                .orElseThrow(() -> new OrderDetailNotFoundException());
        return order;
    }

    private Long generateOrderId() {
        String orderDatePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int subfixLength = orderProperties.getSubfixLength();
        int tryCount = orderProperties.getTryCount();
        Long orderId = 0L;
        int count = 0;

        while (count++ < tryCount) {

            String randomNum = String.valueOf(System.nanoTime() % 1_000_000);
            StringBuilder sb = new StringBuilder(orderDatePrefix);

            if (randomNum.length() < subfixLength) {
                sb.append("0");
                sb.append(randomNum);
            } else {
                sb.append(randomNum);
            }

            orderId = Long.valueOf(sb.toString());

            if (isOrderIdNonExists(orderId) == false) {
                return orderId;
            }

            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                throw new OrderIdGenerateFailedException();
            }
        }

        throw new OrderIdGenerateFailedException();
    }

    private boolean isOrderIdNonExists(Long orderId) {
        return orderDetailRepo.existsById(orderId);
    }

}
