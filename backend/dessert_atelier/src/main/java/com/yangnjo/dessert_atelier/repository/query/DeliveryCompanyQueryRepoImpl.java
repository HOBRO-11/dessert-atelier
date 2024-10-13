package com.yangnjo.dessert_atelier.repository.query;

import static com.yangnjo.dessert_atelier.domain.delivery.QDeliveryCompany.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.DeliveryCompanyDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliveryCompanyQueryRepoImpl {
  
  private final JPAQueryFactory queryFactory;

  public List<DeliveryCompanyDto> findAll() {
    return queryFactory.select(DeliveryCompanyDto.asDto()).from(deliveryCompany).fetch();
  }
}
