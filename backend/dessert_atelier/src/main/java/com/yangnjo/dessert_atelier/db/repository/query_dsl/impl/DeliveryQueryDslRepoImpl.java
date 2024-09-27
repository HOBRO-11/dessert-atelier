package com.yangnjo.dessert_atelier.db.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.constructor;
import static com.yangnjo.dessert_atelier.db.entity.QDeliveries.deliveries;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.dto.delivery.DeliveryDto;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.DeliveryQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeliveryQueryDslRepoImpl implements DeliveryQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DeliveryDto> findByCondition(int page, int size, String code, Direction direction) {
        return queryFactory
                .select(constructor(DeliveryDto.class, deliveries.code, deliveries.company.companyName,
                        deliveries.createdAt))
                .from(deliveries)
                .where(isCode(code))
                .offset(page * size)
                .limit(size)
                .orderBy(sort(direction))
                .fetch();
    }

    private BooleanExpression isCode(String code) {
        return code == null ? null : deliveries.code.eq(code);
    }

    private OrderSpecifier<LocalDateTime> sort(Direction direction) {
        if (direction.isAscending()) {
            return deliveries.createdAt.asc();
        }
        return deliveries.createdAt.desc();
    }
}
