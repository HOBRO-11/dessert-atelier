package com.yangnjo.dessert_atelier.db.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.constructor;
import static com.yangnjo.dessert_atelier.db.entity.QProducts.products;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.dto.product.ProductDto;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.ProductQueryDalRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductQueryDalRepoImpl implements ProductQueryDalRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductDto> findProductsWithStatus(int page, int size, ProductStatus status) {
        return queryFactory
                .select(constructor(ProductDto.class, products.id, products.name, products.price, products.thumb))
                .from(products)
                .where(isStatus(status))
                .offset(page * size)
                .limit(size)
                .fetch();
    }

    private BooleanExpression isStatus(ProductStatus status) {
        return status == null ? products.status.eq(ProductStatus.SALE) : products.status.eq(status);
    }
}
