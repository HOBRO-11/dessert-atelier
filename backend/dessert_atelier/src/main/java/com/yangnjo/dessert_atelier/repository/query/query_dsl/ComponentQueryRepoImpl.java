package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.product.QComponent.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.ComponentDto;
import com.yangnjo.dessert_atelier.repository.query.ComponentQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ComponentQueryRepoImpl implements ComponentQueryRepo {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<ComponentDto> findByIds(List<Long> ids) {
    return queryFactory.select(ComponentDto.asDto())
        .from(component)
        .where(component.id.in(ids))
        .fetch();
  }
}
