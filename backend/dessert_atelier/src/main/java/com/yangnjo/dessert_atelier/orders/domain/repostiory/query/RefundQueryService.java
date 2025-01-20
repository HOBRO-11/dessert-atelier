package com.yangnjo.dessert_atelier.orders.domain.repostiory.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.orders.domain.entity.RefundStatus;
import com.yangnjo.dessert_atelier.orders.dto.RefundDto;
import com.yangnjo.dessert_atelier.orders.dto.RefundSimpleDto;

public interface RefundQueryService {
    
    List<RefundDto> findAllByOrderId(Long orderId);

    Optional<RefundDto> findById(Long refundId);
    
    List<RefundSimpleDto> findAllByStatus(RefundStatus status, PageOption pageOption);
    
    Long countAllByStatus(RefundStatus status);

}
