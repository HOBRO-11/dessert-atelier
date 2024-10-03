package com.yangnjo.dessert_atelier.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.*;
import static com.yangnjo.dessert_atelier.domain.QOptions.*;
import static com.yangnjo.dessert_atelier.domain.QProductQuantity.*;

import java.util.List;
import java.util.stream.Collectors;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.OptionStatus;
import com.yangnjo.dessert_atelier.repository.dto.OptionFlatDto;
import com.yangnjo.dessert_atelier.repository.dto.OptionSimpleDto;
import com.yangnjo.dessert_atelier.repository.query_dsl.OptionQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OptionQueryDslRepoImpl implements OptionQueryDslRepo {

        private final JPAQueryFactory queryFactory;

        @Override
        public List<OptionSimpleDto> searchSimplesWithCondition(Long dpId) {
                return queryFactory
                                .select(constructor(OptionSimpleDto.class, options.id, options.description,
                                                options.status,
                                                options.price))
                                .from(options)
                                .where(isDisplayProduct(dpId))
                                .fetch();
        }

    @Override
    public List<OptionFlatDto> searchWithCondition(Long dpId) {
        List<Tuple> results = queryFactory
                .select(options.id, options.displayProducts.id, options.description,
                        options.totalQuantity,
                        options.status, options.price)
                .from(options)
                .where(isDisplayProduct(dpId))
                .fetch();

        List<Long> optionIds = results
                .stream()
                .map(t -> t.get(0, Long.class))
                .collect(Collectors.toList());

        Long[] a = new Long[optionIds.size()];
                
        List<Long> pqIds = queryFactory
                .select(productQuantity.id)
                .from(productQuantity)
                .where(productQuantity.id.in(optionIds.toArray(a)))
                .fetch();

        return results.stream()
                .map(tuple -> new OptionFlatDto(tuple.get(0, Long.class),
                        tuple.get(1, Long.class),
                        tuple.get(2, String.class),
                        tuple.get(3, Integer.class),
                        tuple.get(4, OptionStatus.class),
                        tuple.get(5, Integer.class),
                        pqIds))
                .collect(Collectors.toList());
    }

        private BooleanExpression isDisplayProduct(Long dpId) {
                return options.displayProducts.id.eq(dpId);
        }
}
