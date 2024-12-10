package com.yangnjo.dessert_atelier.repository.total.query.query_dsl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_model.total.QTotalSaleOption;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleOptionDto;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleOptionGraphDto;
import com.yangnjo.dessert_atelier.repository.total.query.TotalSaleOptionQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TotalSaleOptionQueryRepoImpl implements TotalSaleOptionQueryRepo {

    private final JPAQueryFactory queryFactory;
    QTotalSaleOption totalSaleOption = QTotalSaleOption.totalSaleOption;

    @Override
    public List<TotalSaleOptionDto> findForPageByOptionId(Long optionId, PageOption pageOption,
            PeriodOption periodOption) {
        return queryFactory.select(TotalSaleOptionDto.asDto())
                .from(totalSaleOption)
                .where(equalOptionId(optionId), between(periodOption))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(totalSaleOption.createdAt))
                .fetch();
    }

    @Override
    public Long countForPageByOptionId(Long optionId, PeriodOption periodOption) {
        return queryFactory.select(totalSaleOption.count())
                .from(totalSaleOption)
                .where(equalOptionId(optionId), between(periodOption))
                .fetchOne();
    }

    @Override
    public List<TotalSaleOptionGraphDto> findForGraphByOptionId(Long optionId, PeriodOption periodOption) {
        return queryFactory.select(TotalSaleOptionGraphDto.asDto())
                .from(totalSaleOption)
                .where(equalOptionId(optionId), between(periodOption))
                .orderBy(ByCreatedAtToASC())
                .fetch();
    }

    private OrderSpecifier<LocalDate> ByCreatedAtToASC() {
        return totalSaleOption.createdAt.asc();
    }

    private BooleanExpression equalOptionId(Long optionId) {
        return optionId == null ? null : totalSaleOption.option.id.eq(optionId);
    }

    private BooleanExpression between(PeriodOption periodOption) {
        return periodOption == null ? null
                : totalSaleOption.createdAt.between(periodOption.getStart().toLocalDate(),
                        periodOption.getEnd().toLocalDate());
    }
}
