package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.delivery.QDelivery.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.DeliveryDto;
import com.yangnjo.dessert_atelier.repository.query.DeliveryQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliveryQueryRepoImpl implements DeliveryQueryRepo {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<DeliveryDto> findByDeliveryId(Long deliveryId) {
    return Optional.ofNullable(
        queryFactory.select(DeliveryDto.asDto()).from(delivery).where(equalDeliveryId(deliveryId)).fetchOne());
  }

  private BooleanExpression equalDeliveryId(Long deliveryId) {
    return delivery.id.eq(deliveryId);
  }
}
