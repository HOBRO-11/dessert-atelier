package com.yangnjo.dessert_atelier.db.repository.query_dsl.impl;

import static com.yangnjo.dessert_atelier.db.entity.QProducts.products;
import static com.yangnjo.dessert_atelier.db.entity.QProductImages.productImages;

import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
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

    // TODO 나중에 이름 바꾸기
    @Override
    public List<ProductSimpleDto> findProductsWithStatus(int page, int size, ProductStatus status) {
        return queryFactory
                .select(constructor(ProductSimpleDto.class, products.id, products.name, products.price, products.thumb))
                .from(products)
                .where(isStatus(status))
                .offset(page * size)
                .limit(size)
                .fetch();
    }

    @Override
    public List<ProductDetailDto> findDetailProduct(Long id) {
        return queryFactory
                .select(constructor(ProductDetailDto.class, products.id, products.name, products.price,
                        products.quantity, products.status, products.thumb, products.comment,
                        productImages.imagesUrl))
                .from(products)
                .leftJoin(products.images, productImages)
                .where(isProductId(id))
                .fetch();
    }

    @Override
    public ProductDetailDtoExceptImages findProductExceptImages(Long id) {
        return queryFactory
                .select(constructor(ProductDetailDtoExceptImages.class, products.id, products.name, products.price,
                        products.quantity, products.status, products.thumb, products.comment))
                .from(products)
                .where(isProductId(id))
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

}
