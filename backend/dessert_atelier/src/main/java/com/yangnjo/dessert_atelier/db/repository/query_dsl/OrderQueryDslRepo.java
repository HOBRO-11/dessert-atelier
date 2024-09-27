package com.yangnjo.dessert_atelier.db.repository.query_dsl;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.model.OrderStatus;

public interface OrderQueryDslRepo {

    List<OrderDto> findByCondition(int page, int size, Users user, OrderStatus status, Direction direction);

    Long countFindByCondition(Users user, OrderStatus status);

}