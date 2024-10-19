package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.total.QTotalSaleProduct.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.dto.TotalSaleProductDto;
import com.yangnjo.dessert_atelier.repository.dto.TotalSaleProductGraphDto;
import com.yangnjo.dessert_atelier.repository.query.TotalSaleProductQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TotalSaleProductQueryRepoImpl implements TotalSaleProductQueryRepo {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<TotalSaleProductDto> findForPageByProductId(Long productId, PageOption pageOption, PeriodOption periodOption) {
    return queryFactory.select(TotalSaleProductDto.asDto())
        .from(totalSaleProduct)
        .where(equalProductId(productId), between(periodOption))
        .offset(pageOption.getOffset())
        .limit(pageOption.getSize())
        .orderBy(pageOption.getDirection(totalSaleProduct.createdAt))
        .fetch();
  }

  @Override
  public Long countForPageByProductId(Long productId, PeriodOption periodOption) {
    return queryFactory.select(totalSaleProduct.count())
        .from(totalSaleProduct)
        .where(equalProductId(productId), between(periodOption))
        .fetchOne();
  }

  @Override
  public List<TotalSaleProductGraphDto> findForGraphByProductId(Long productId, PeriodOption periodOption) {
    return queryFactory.select(TotalSaleProductGraphDto.asDto())
        .from(totalSaleProduct)
        .where(equalProductId(productId), between(periodOption))
        .orderBy(ByCreatedAtToASC())
        .fetch();
  }

  private OrderSpecifier<LocalDate> ByCreatedAtToASC() {
    return totalSaleProduct.createdAt.asc();
  }


  private BooleanExpression equalProductId(Long productId) {
    return productId == null ? null : totalSaleProduct.product.id.eq(productId);
  }

  private BooleanExpression between(PeriodOption periodOption) {
    return periodOption == null ? null
        : totalSaleProduct.createdAt.between(periodOption.getStart().toLocalDate(),
            periodOption.getEnd().toLocalDate());
  }
}
