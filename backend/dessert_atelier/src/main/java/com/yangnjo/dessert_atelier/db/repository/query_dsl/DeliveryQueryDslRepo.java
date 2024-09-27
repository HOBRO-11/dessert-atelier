package com.yangnjo.dessert_atelier.db.repository.query_dsl;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.yangnjo.dessert_atelier.common.dto.delivery.DeliveryDto;

public interface DeliveryQueryDslRepo {

    List<DeliveryDto> findByCondition(int page, int size, String code, Direction direction);

}