package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.delivery.QDeliveryCompany.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.DeliveryCompanyDto;
import com.yangnjo.dessert_atelier.repository.query.DeliveryCompanyQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliveryCompanyQueryRepoImpl implements DeliveryCompanyQueryRepo {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<DeliveryCompanyDto> findAll() {
    return queryFactory.select(DeliveryCompanyDto.asDto()).from(deliveryCompany).fetch();
  }

  @Override
  public Optional<DeliveryCompanyDto> find(Long deliveryCompanyId) {
    return Optional.ofNullable(queryFactory.select(DeliveryCompanyDto.asDto()).from(deliveryCompany)
        .where(equalDeliveryCompanyId(deliveryCompanyId)).fetchOne());
  }

  private BooleanExpression equalDeliveryCompanyId(Long deliveryCompanyId) {
    return deliveryCompany.id.eq(deliveryCompanyId);
  }
}
