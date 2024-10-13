package com.yangnjo.dessert_atelier.repository.query;

import static com.yangnjo.dessert_atelier.domain.order.QOptionQuantity.*;
import static com.yangnjo.dessert_atelier.domain.order.QOrders.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.PageOption;
import com.yangnjo.dessert_atelier.repository.PeriodOption;
import com.yangnjo.dessert_atelier.repository.dto.OrderDestinationDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderDetailDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderDetailDto.OrderPropertyDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderSimpleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepoImpl {
  private final JPAQueryFactory queryFactory;

  @Transactional(readOnly = true)
  public List<OrderSimpleDto> findSimpleByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption) {
    List<OrderSimpleDto> dtos = queryFactory.select(OrderSimpleDto.asIncompleteDto())
        .from(orders)
        .where(equalMemberId(memberId), betweenPeriod(periodOption))
        .fetch();

    dtos.stream().map(dto -> {
      String orderCode = dto.getOrderCode();
      String title = queryFactory.select(optionQuantity.displayProductPreset.title)
          .from(optionQuantity)
          .where(equalOrderCodeAtOptionQuantity(orderCode))
          .fetchFirst();
      dto.setDppTitle(title);
      return dto;

    }).collect(Collectors.toList());

    return dtos;
  }

  private BooleanExpression equalOrderCodeAtOptionQuantity(String orderCode) {
    return optionQuantity.orders.orderCode.eq(orderCode);
  }

  public Optional<OrderDetailDto> findDetailByOrderCode(String orderCode) {
    OrderDetailDto dto = queryFactory
        .select(OrderDetailDto.asIncompleteDto())
        .from(orders)
        .where(equalOrderCode(orderCode))
        .fetchOne();

    if (dto == null) {
      return Optional.empty();
    }

    List<OrderPropertyDto> opDtos = queryFactory
        .select(OrderPropertyDto.asDto())
        .from(optionQuantity)
        .where(equalOrderCode(orderCode))
        .fetch();
    dto.setOrderPropertyDtos(opDtos);

    return Optional.of(dto);
  }

  public Optional<OrderDestinationDto> findDestinationByOrderCode(String orderCode) {
    return Optional.ofNullable(queryFactory
        .select(OrderDestinationDto.asDto())
        .from(orders)
        .where(equalOrderCode(orderCode))
        .fetchOne());

  }

  private BooleanExpression equalOrderCode(String orderCode) {
    return orders.orderCode.eq(orderCode);
  }

  private BooleanExpression equalMemberId(Long memberId) {
    return orders.member.id.eq(memberId);
  }

  private BooleanExpression betweenPeriod(PeriodOption periodOption) {
    return periodOption != null ? orders.createdAt.between(periodOption.getStart(), periodOption.getEnd()) : null;
  }
}
