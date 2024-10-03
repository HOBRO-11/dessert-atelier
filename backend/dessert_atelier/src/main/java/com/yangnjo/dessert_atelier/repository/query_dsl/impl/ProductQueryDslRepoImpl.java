package com.yangnjo.dessert_atelier.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.*;
import static com.yangnjo.dessert_atelier.domain.QProducts.*;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.ProductStatus;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.ProductFlatDto;
import com.yangnjo.dessert_atelier.repository.query_dsl.ProductQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductQueryDslRepoImpl implements ProductQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
	public List<ProductFlatDto> searchWithCondition(PageOption pageOption, Long productId, ProductStatus status) {
        return queryFactory
                .select(constructor(ProductFlatDto.class, products.id, products.name, products.status, products.price,
                        products.thumb))
                .from(products)
                .where(condition(productId, status))
                .offset(pageOption.getPage() * pageOption.getSize())
                .limit(pageOption.getSize())
                .orderBy(sort(pageOption.getDirection()))
                .fetch();
    }

    @Override
	public Long countWithCondition(Long productId, ProductStatus status) {
        return queryFactory
                .select(products.count())
                .from(products)
                .where(condition(productId, status))
                .fetchOne();
    }

    private OrderSpecifier<?> sort(Direction direction) {
        if (direction == null) {
            return products.id.desc();
        }
        if (direction.isAscending()) {
            return products.id.asc();
        }
        return products.id.desc();
    }

    private BooleanExpression condition(Long productId, ProductStatus status) {
        if (productId == null && status == null) {
            return null;
        }

        if (productId == null) {
            return products.status.eq(status);
        }

        if (status == null) {
            return products.id.eq(productId);
        }

        return products.id.eq(productId).and(products.status.eq(status));
    }
}
