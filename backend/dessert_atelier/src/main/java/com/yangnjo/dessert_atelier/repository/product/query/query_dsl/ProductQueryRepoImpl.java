package com.yangnjo.dessert_atelier.repository.product.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.QProduct;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductDto;
import com.yangnjo.dessert_atelier.repository.product.query.ProductQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepoImpl implements ProductQueryRepo {

    private final JPAQueryFactory queryFactory;
    QProduct product = QProduct.product;

    @Override
    public List<ProductDto> find(PageOption pageOption) {
        return queryFactory.select(ProductDto.asDto())
                .from(product)
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(product.id))
                .fetch();
    }

    @Override
    public Optional<ProductDto> findByProductId(Long productId) {
        return Optional.ofNullable(queryFactory.select(ProductDto.asDto())
                .from(product)
                .where(equalId(productId))
                .fetchOne());
    }

    private BooleanExpression equalId(Long productId) {
        return product.id.eq(productId);
    }

}
