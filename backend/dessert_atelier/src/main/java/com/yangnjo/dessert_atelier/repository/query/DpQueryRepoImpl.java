package com.yangnjo.dessert_atelier.repository.query;

import static com.yangnjo.dessert_atelier.domain.display_product.QDisplayProduct.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.repository.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.DpDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DpQueryRepoImpl {

    private final JPAQueryFactory queryFactory;

    public List<DpDto> findAll(PageOption pageOption) {
        return queryFactory.select(DpDto.asDto())
                .from(displayProduct)
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(displayProduct.id))
                .fetch();
    }

    public Long countAll() {
        return queryFactory.select(displayProduct.count())
                .from(displayProduct)
                .fetchOne();
    }

    public List<DpDto> findBySaleStatusAndLikeNaming(SaleStatus saleStatus, String naming, PageOption pageOption) {
        return queryFactory.select(DpDto.asDto())
                .from(displayProduct)
                .where(equalSaleStatus(saleStatus), likeNaming(naming))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(displayProduct.id))
                .fetch();
    }

    public Long countBySaleStatusAndLikeNaming(SaleStatus saleStatus, String naming) {
        return queryFactory.select(displayProduct.count())
                .from(displayProduct)
                .where(equalSaleStatus(saleStatus), likeNaming(naming))
                .fetchOne();
    }

    public List<DpDto> findByIds(List<Long> ids){
        return queryFactory.select(DpDto.asDto())
                .from(displayProduct)
                .where(inIds(ids))
                .fetch();
    }

    private BooleanExpression inIds(List<Long> ids) {
        return ids != null && !ids.isEmpty() ? displayProduct.id.in(ids) : null;
    }

    private BooleanExpression likeNaming(String naming) {
        return naming != null ? displayProduct.naming.like(naming) : null;
    }

    private BooleanExpression equalSaleStatus(SaleStatus saleStatus) {
        return saleStatus != null ? displayProduct.saleStatus.eq(saleStatus) : null;
    }
}
