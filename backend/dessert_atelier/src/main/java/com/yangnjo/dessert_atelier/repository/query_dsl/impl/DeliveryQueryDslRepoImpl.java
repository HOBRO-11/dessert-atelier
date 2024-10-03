package com.yangnjo.dessert_atelier.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.*;
import static com.yangnjo.dessert_atelier.domain.QDeliveries.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.DeliveryFlatDto;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.query_dsl.DeliveryQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeliveryQueryDslRepoImpl implements DeliveryQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DeliveryFlatDto> searchWithCondition(PageOption pageOption, String code) {
        return queryFactory
                .select(constructor(DeliveryFlatDto.class, deliveries.code, deliveries.company.id,
                        deliveries.company.companyName, deliveries.company.phone))
                .from(deliveries)
                .where(isCode(code))
                .offset(pageOption.getPage() * pageOption.getSize())
                .limit(pageOption.getSize())
                .orderBy(sort(pageOption.getDirection()))
                .fetch();
    }

    @Override
    public Long countWithCondition(String code) {
        return queryFactory
                .select(deliveries.count())
                .from(deliveries)
                .where(isCode(code))
                .fetchOne();
    }

    private BooleanExpression isCode(String code) {
        return code == null ? null : deliveries.code.eq(code);
    }

    private OrderSpecifier<LocalDateTime> sort(Direction direction) {
        if (direction == null) {
            return deliveries.createdAt.desc();
        }
        if (direction.isAscending()) {
            return deliveries.createdAt.asc();
        }
        return deliveries.createdAt.desc();
    }
}
