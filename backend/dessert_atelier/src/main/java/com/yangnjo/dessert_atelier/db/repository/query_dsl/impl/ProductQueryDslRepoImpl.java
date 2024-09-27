package com.yangnjo.dessert_atelier.db.repository.query_dsl.impl;

import static com.yangnjo.dessert_atelier.db.entity.QProducts.products;
import static com.yangnjo.dessert_atelier.db.entity.QProductImages.productImages;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import static com.querydsl.core.types.Projections.constructor;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.dto.product.ProductDetailDto;
import com.yangnjo.dessert_atelier.common.dto.product.ProductDetailDtoExceptImages;
import com.yangnjo.dessert_atelier.common.dto.product.ProductSimpleDto;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.ProductQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductQueryDslRepoImpl implements ProductQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductSimpleDto> findSimpleProductsByStatus(int page, int size, ProductStatus status, Direction direction) {
        return queryFactory
                .select(constructor(ProductSimpleDto.class, products.id, products.name, products.price, products.thumb))
                .from(products)
                .where(isStatus(status))
                .offset(page * size)
                .limit(size)
                .orderBy(sort(direction))
                .fetch();
    }

    @Override
    public List<ProductDetailDto> findDetailProducts(Long id, Direction direction) {
        return queryFactory
                .select(constructor(ProductDetailDto.class, products.id, products.name, products.price,
                        products.quantity, products.status, products.thumb, products.comment,
                        productImages.imagesUrl))
                .from(products)
                .leftJoin(products.images, productImages)
                .where(isProductId(id))
                .orderBy(sort(direction))
                .fetch();
    }

    @Override
    public ProductDetailDtoExceptImages findProductsExceptImages(Long id, Direction direction) {
        return queryFactory
                .select(constructor(ProductDetailDtoExceptImages.class, products.id, products.name, products.price,
                        products.quantity, products.status, products.thumb, products.comment))
                .from(products)
                .where(isProductId(id))
                .orderBy(sort(direction))
                .fetchOne();
    }

    private BooleanExpression isProductId(Long id) {
        if (id == null) {
            // TODO id 값을 받지 못함
            throw new RuntimeException();
        }
        return products.id.eq(id);
    }

    private BooleanExpression isStatus(ProductStatus status) {
        return status == null ? null : products.status.eq(status);
    }

    private OrderSpecifier<LocalDateTime> sort(Direction direction) {
        if (direction.isAscending()) {
            return products.createdAt.asc();
        }
        return products.createdAt.desc();
    }

}
