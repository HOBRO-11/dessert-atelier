package com.yangnjo.dessert_atelier.sale.domain.repository.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.commons.util.page.PageResponse;
import com.yangnjo.dessert_atelier.sale.domain.entity.QProduct;
import com.yangnjo.dessert_atelier.sale.domain.repository.query.ProductQueryService;
import com.yangnjo.dessert_atelier.sale.dto.ProductDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductQueryServiceV1 implements ProductQueryService {

    private final JPAQueryFactory queryFactory;
    QProduct product = QProduct.product;

    @Override
    public Page<ProductDto> findAll(PageOption pageOption) {
        List<ProductDto> content = queryFactory.select(ProductDto.asDto())
                .from(product)
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(product.id))
                .fetch();

        return PageResponse.of(content, pageOption, () -> count());
    }

    private Long count() {
        return queryFactory.select(product.count())
                .from(product)
                .fetchOne();
    }

    @Override
    public Optional<ProductDto> findByProductId(Long productId) {
        return Optional.ofNullable(queryFactory.select(ProductDto.asDto())
                .from(product)
                .where(equalId(productId))
                .fetchOne());
    }

    private BooleanExpression equalId(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("productId를 입력해주세요.");
        }
        return product.id.eq(productId);
    }

}
