package com.yangnjo.dessert_atelier.repository.query;

import static com.yangnjo.dessert_atelier.domain.product.QProduct.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;
import com.yangnjo.dessert_atelier.repository.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.ProductDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepoImpl {

  private final JPAQueryFactory queryFactory;

  public List<ProductDto> findByStatus(ProductStatus status, PageOption pageOption) {
    return queryFactory.select(ProductDto.asDto())
        .from(product)
        .where(equalProductStatus(status))
        .offset(pageOption.getOffset())
        .limit(pageOption.getSize())
        .orderBy(pageOption.getDirection(product.id))
        .fetch();
  }

  private BooleanExpression equalProductStatus(ProductStatus status) {
    return status != null ? product.productStatus.eq(status) : null;
  }
}
