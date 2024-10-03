package com.yangnjo.dessert_atelier.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.*;
import static com.yangnjo.dessert_atelier.domain.QDisplayProducts.*;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.DpFlatDto;
import com.yangnjo.dessert_atelier.repository.dto.DpSimpleDto;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.query_dsl.DpQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DpQueryDslRepoImpl implements DpQueryDslRepo {

        private final JPAQueryFactory queryFactory;

        @Override
        public Optional<DpFlatDto> searchWithCondition(PageOption pageOption, Long dpId) {
                return Optional.ofNullable(queryFactory
                                .select(constructor(DpFlatDto.class, displayProducts.id, displayProducts.title,
                                                displayProducts.price,
                                                displayProducts.thumb, displayProducts.description,
                                                displayProducts.saleStatus,
                                                displayProducts.dpStatus, displayProducts.dpImgs.imagesUrl,
                                                displayProducts.createdAt, displayProducts.updatedAt))
                                .from(displayProducts)
                                .where(isDisplayProduct(dpId))
                                .fetchOne());

                                
        }

        @Override
        public List<DpSimpleDto> searchSimplesWithCondition(PageOption pageOption, Long dpId) {
                return queryFactory
                                .select(constructor(DpSimpleDto.class, displayProducts.id, displayProducts.title,
                                                displayProducts.price,
                                                displayProducts.thumb, displayProducts.saleStatus,
                                                displayProducts.dpStatus))
                                .from(displayProducts)
                                .where(isDisplayProduct(dpId))
                                .fetch();
        }

        private BooleanExpression isDisplayProduct(Long dpId) {
                return displayProducts.id.eq(dpId);
        }
}
