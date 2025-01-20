package com.yangnjo.dessert_atelier.orders.domain.domain_service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.orders.domain.domain_service.RefundService;
import com.yangnjo.dessert_atelier.orders.domain.domain_service.RefundValidator;
import com.yangnjo.dessert_atelier.orders.domain.entity.OrderDetail;
import com.yangnjo.dessert_atelier.orders.domain.entity.Refund;
import com.yangnjo.dessert_atelier.orders.domain.entity.RefundStatus;
import com.yangnjo.dessert_atelier.orders.domain.repostiory.OrderDetailRepository;
import com.yangnjo.dessert_atelier.orders.domain.repostiory.RefundRepository;
import com.yangnjo.dessert_atelier.orders.dto.RefundCreateDto;
import com.yangnjo.dessert_atelier.orders.exception.OrderDetailNotFoundException;
import com.yangnjo.dessert_atelier.orders.exception.RefundNotFoundException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Transactional
public class RefundServiceV1 implements RefundService {

    private final RefundRepository refundRepo;
    private final OrderDetailRepository orderDetailRepo;
    private final RefundValidator refundValidator;

    @Override
    public Long create(RefundCreateDto dto) {
        OrderDetail orderDetail = findOrderDetailByOrderId(dto.getOrderId());
        dto.getProperties().forEach(prop -> {
            refundValidator.validate(orderDetail, prop);
        });
        Refund refund = dto.toEntity(orderDetail);
        return refundRepo.save(refund).getId();
    }

    @Override
    public void delete(Long refundId) {
        Refund refund = findRefundById(refundId);
        refundRepo.delete(refund);
    }

    @Override
    public void updateStatus(Long refundId, RefundStatus status) {
        Refund refund = findRefundById(refundId);
        refund.setStatus(status);
    }

    private Refund findRefundById(Long refundId) {
        return refundRepo.findById(refundId)
                .orElseThrow(() -> new RefundNotFoundException());
    }

    private OrderDetail findOrderDetailByOrderId(Long orderId) {
        return orderDetailRepo.findById(orderId)
                .orElseThrow(() -> new OrderDetailNotFoundException(String.valueOf(orderId)));
    }
}
