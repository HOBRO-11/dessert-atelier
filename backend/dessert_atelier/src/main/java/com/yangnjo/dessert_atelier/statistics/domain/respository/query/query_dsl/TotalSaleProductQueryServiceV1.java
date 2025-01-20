package com.yangnjo.dessert_atelier.statistics.domain.respository.query.query_dsl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.commons.util.page.PeriodOption;
import com.yangnjo.dessert_atelier.statistics.domain.entity.QTotalSaleProduct;
import com.yangnjo.dessert_atelier.statistics.domain.respository.query.TotalSaleProductQueryService;
import com.yangnjo.dessert_atelier.statistics.dto.TotalSaleProductDto;
import com.yangnjo.dessert_atelier.statistics.dto.TotalSaleProductGraphDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TotalSaleProductQueryServiceV1 implements TotalSaleProductQueryService {

    private final JPAQueryFactory queryFactory;
    QTotalSaleProduct totalSaleProduct = QTotalSaleProduct.totalSaleProduct;

    @Override
    public List<TotalSaleProductDto> findForPageByProductId(Long productId, PageOption pageOption,
            PeriodOption periodOption) {
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
        return productId == null ? null : totalSaleProduct.productId.eq(productId);
    }

    private BooleanExpression between(PeriodOption periodOption) {
        return periodOption == null ? null
                : totalSaleProduct.createdAt.between(periodOption.getStart().toLocalDate(),
                        periodOption.getEnd().toLocalDate());
    }
}
