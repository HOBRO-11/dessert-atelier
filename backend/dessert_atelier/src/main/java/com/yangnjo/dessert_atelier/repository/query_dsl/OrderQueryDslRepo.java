package com.yangnjo.dessert_atelier.repository.query_dsl;

import java.util.List;

import com.yangnjo.dessert_atelier.domain.OrderStatus;
import com.yangnjo.dessert_atelier.repository.dto.DateOption;
import com.yangnjo.dessert_atelier.repository.dto.OrderFlatDto;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;

public interface OrderQueryDslRepo {

    List<OrderFlatDto> searchWithCondition(PageOption pageOption, DateOption dateOption, Long userId, Long dpId,
            OrderStatus status);

    Long countWithCondition(Long userId, Long dpId, OrderStatus status);

}