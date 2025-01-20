package com.yangnjo.dessert_atelier.orders.domain.domain_service;

import com.yangnjo.dessert_atelier.orders.domain.entity.RefundStatus;
import com.yangnjo.dessert_atelier.orders.dto.RefundCreateDto;

public interface RefundService {

    public Long create(RefundCreateDto dto);

    public void updateStatus(Long refundId, RefundStatus status);

    public void delete(Long refundId);

}
