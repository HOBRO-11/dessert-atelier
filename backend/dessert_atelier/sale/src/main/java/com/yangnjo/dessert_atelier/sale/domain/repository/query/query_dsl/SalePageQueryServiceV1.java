package com.yangnjo.dessert_atelier.sale.domain.repository.query.query_dsl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.sale.domain.entity.QSalePage;
import com.yangnjo.dessert_atelier.sale.domain.repository.query.SalePageQueryService;
import com.yangnjo.dessert_atelier.sale.dto.SalePageDto;
import com.yangnjo.dessert_atelier.sale.dto.SalePageSimpleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SalePageQueryServiceV1 implements SalePageQueryService {

    private final JPAQueryFactory queryFactory;
    QSalePage salePage = QSalePage.salePage;

    @Override
    public List<SalePageSimpleDto> findAllSimpleByIdIn(Collection<Long> ids) {
        return queryFactory
                .select(SalePageSimpleDto.asDto())
                .from(salePage)
                .where(inIds(ids))
                .fetch();
    }

    @Override
    public Optional<SalePageDto> findById(Long id) {
        return Optional.ofNullable(
                queryFactory
                        .select(SalePageDto.asDto())
                        .from(salePage)
                        .where(equalId(id))
                        .fetchOne());
    }

    private BooleanExpression equalId(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("id를 입력해주세요.");
        }
        return salePage.id.eq(id);
    }

    private BooleanExpression inIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("ids를 입력해주세요.");
        }
        return salePage.id.in(ids);
    }
}
