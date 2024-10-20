package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.order.QOptionQuantity.*;
import static com.yangnjo.dessert_atelier.domain.order.QOrders.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.dto.OrderDestinationDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderDetailDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderDetailDto.OrderPropertyDto;
import com.yangnjo.dessert_atelier.repository.query.OrderQueryRepo;
import com.yangnjo.dessert_atelier.repository.dto.OrderSimpleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepoImpl implements OrderQueryRepo {
  private final JPAQueryFactory queryFactory;

  @Override
  @Transactional(readOnly = true)
  public List<OrderSimpleDto> findSimpleByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption) {
    List<OrderSimpleDto> dtos = queryFactory.select(OrderSimpleDto.asIncompleteDto())
        .from(orders)
        .where(equalMemberId(memberId), betweenPeriod(periodOption))
        .fetch();

    dtos.stream().map(dto -> {
      Long orderCode = dto.getOrderCode();
      String title = queryFactory.select(optionQuantity.displayProductPreset.title)
          .from(optionQuantity)
          .where(equalOrderCodeAtOptionQuantity(orderCode))
          .fetchFirst();
      dto.setDppTitle(title);
      return dto;

    }).collect(Collectors.toList());

    return dtos;
  }

  private BooleanExpression equalOrderCodeAtOptionQuantity(Long orderCode) {
    return optionQuantity.orders.orderCode.eq(orderCode);
  }

  @Override
  public Optional<OrderDetailDto> findDetailByOrderCode(Long orderCode) {
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

  @Override
  public Optional<OrderDestinationDto> findDestinationByOrderCode(Long orderCode) {
    return Optional.ofNullable(queryFactory
        .select(OrderDestinationDto.asDto())
        .from(orders)
        .where(equalOrderCode(orderCode))
        .fetchOne());

  }

  private BooleanExpression equalOrderCode(Long orderCode) {
    return orders.orderCode.eq(orderCode);
  }

  private BooleanExpression equalMemberId(Long memberId) {
    return orders.member.id.eq(memberId);
  }

  private BooleanExpression betweenPeriod(PeriodOption periodOption) {
    return periodOption != null ? orders.createdAt.between(periodOption.getStart(), periodOption.getEnd()) : null;
  }
}
