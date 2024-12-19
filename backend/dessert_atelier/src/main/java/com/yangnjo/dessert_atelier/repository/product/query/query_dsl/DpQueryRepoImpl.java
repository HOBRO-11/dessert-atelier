package com.yangnjo.dessert_atelier.repository.product.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.domain_model.product.QDisplayProduct;
import com.yangnjo.dessert_atelier.repository.product.dto.DpDto;
import com.yangnjo.dessert_atelier.repository.product.dto.DpSimpleDto;
import com.yangnjo.dessert_atelier.repository.product.query.DpQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DpQueryRepoImpl implements DpQueryRepo {

    private final JPAQueryFactory queryFactory;
    QDisplayProduct displayProduct = QDisplayProduct.displayProduct;

    @Override
    public List<DpSimpleDto> findAllSimpleByDpStatus(DisplayProductStatus displayProductStatus, PageOption pageOption) {
        return queryFactory
                .select(DpSimpleDto.asDto())
                .from(displayProduct)
                .where(equalDpStatus(displayProductStatus))
                .limit(pageOption.getSize())
                .offset(pageOption.getOffset())
                .orderBy(pageOption.getDirection(displayProduct.updatedAt))
                .fetch();
    }

    @Override
    public Long countByDpStatus(DisplayProductStatus displayProductStatus) {
        return queryFactory
                .select(displayProduct.count())
                .from(displayProduct)
                .where(equalDpStatus(displayProductStatus))
                .fetchOne();
    }

    @Override
    public List<DpSimpleDto> findAllSimpleByIdIn(List<Long> ids) {
        return queryFactory
                .select(DpSimpleDto.asDto())
                .from(displayProduct)
                .where(inIds(ids))
                .fetch();
    }

    @Override
    public Optional<DpDto> findById(Long id) {
        return Optional.ofNullable(
                queryFactory
                        .select(DpDto.asDto())
                        .from(displayProduct)
                        .where(equalDpId(id))
                        .fetchOne());
    }

    @Override
    public List<DpSimpleDto> findAllSimpleByExceptDpStatus(DisplayProductStatus displayProductStatus,
            PageOption pageOption) {
        return queryFactory
                .select(DpSimpleDto.asDto())
                .from(displayProduct)
                .where(exceptDpStatus(displayProductStatus))
                .limit(pageOption.getSize())
                .offset(pageOption.getOffset())
                .orderBy(pageOption.getDirection(displayProduct.updatedAt))
                .fetch();
    }

    @Override
    public Long countByExceptDpStatus(DisplayProductStatus displayProductStatus) {
        return queryFactory
                .select(displayProduct.count())
                .from(displayProduct)
                .where(exceptDpStatus(displayProductStatus))
                .fetchOne();

    }

    private BooleanExpression equalDpId(Long id) {
        return displayProduct.id.eq(id);
    }

    private BooleanExpression inIds(List<Long> ids) {
        return ids != null && !ids.isEmpty() ? displayProduct.id.in(ids) : null;
    }

    private BooleanExpression equalDpStatus(DisplayProductStatus displayProductStatus) {
        return displayProductStatus != null ? displayProduct.displayProductStatus.eq(displayProductStatus) : null;
    }

    private BooleanExpression exceptDpStatus(DisplayProductStatus displayProductStatus) {
        return displayProductStatus != null ? displayProduct.displayProductStatus.ne(displayProductStatus) : null;
    }

}
