package com.yangnjo.dessert_atelier.repository.query;

import static com.yangnjo.dessert_atelier.domain.order.QBasket.*;

import java.util.Comparator;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.order.BasketProperty;
import com.yangnjo.dessert_atelier.repository.dto.BasketDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BasketQueryRepoImpl {

  private final JPAQueryFactory queryFactory;

  public Optional<BasketDto> findByMemberId(Long memberId) {
    BasketDto dto = queryFactory
        .select(BasketDto.asDto())
        .from(basket)
        .fetchOne();

    if (dto == null) {
      return Optional.empty();
    }

    dto.getProperties().sort(intoLatest());

    return Optional.of(dto);
  }

  private Comparator<? super BasketProperty> intoLatest() {
    return (bp1, bp2) -> bp2.getUpdatedAt().compareTo(bp1.getUpdatedAt());
  }
}
