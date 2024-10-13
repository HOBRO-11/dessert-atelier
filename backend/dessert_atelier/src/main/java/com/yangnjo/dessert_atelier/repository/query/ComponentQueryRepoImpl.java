package com.yangnjo.dessert_atelier.repository.query;

import static com.yangnjo.dessert_atelier.domain.product.QComponent.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.ComponentDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ComponentQueryRepoImpl {
  private final JPAQueryFactory queryFactory;

  public List<ComponentDto> findByIds(List<Long> ids) {
    return queryFactory.select(ComponentDto.asDto())
        .from(component)
        .where(component.id.in(ids))
        .fetch();
  }
}
