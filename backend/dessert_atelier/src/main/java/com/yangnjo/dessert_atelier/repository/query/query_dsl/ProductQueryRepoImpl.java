package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.product.QProduct.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;
import com.yangnjo.dessert_atelier.repository.dto.ProductDto;
import com.yangnjo.dessert_atelier.repository.query.ProductQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepoImpl implements ProductQueryRepo {

  private final JPAQueryFactory queryFactory;

  @Override
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
