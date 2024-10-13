package com.yangnjo.dessert_atelier.repository.query;

import static com.yangnjo.dessert_atelier.domain.delivery.QDelivery.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.DeliveryDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliveryQueryRepoImpl {

  private final JPAQueryFactory queryFactory;

  public Optional<DeliveryDto> findByDeliveryCode(String deliveryCode) {
    return Optional.ofNullable(queryFactory.select(DeliveryDto.asDto()).from(delivery).fetchOne());
  }
}
