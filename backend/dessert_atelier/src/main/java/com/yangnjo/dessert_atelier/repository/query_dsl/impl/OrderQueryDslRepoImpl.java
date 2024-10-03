package com.yangnjo.dessert_atelier.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.*;
import static com.yangnjo.dessert_atelier.domain.QDisplayProducts.*;
import static com.yangnjo.dessert_atelier.domain.QOrders.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.OrderStatus;
import com.yangnjo.dessert_atelier.repository.dto.DateOption;
import com.yangnjo.dessert_atelier.repository.dto.OrderFlatDto;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.query_dsl.OrderQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderQueryDslRepoImpl implements OrderQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
	public List<OrderFlatDto> searchWithCondition(PageOption pageOption, DateOption dateOption, Long userId, Long dpId,
            OrderStatus status) {
        return queryFactory
                .select(constructor(OrderFlatDto.class, orders.code, orders.users.id, orders.destination.postCode,
                        orders.destination.detailAddress, orders.destination.receiver, orders.destination.phone,
                        orders.deliveries.code, orders.status, orders.orderCarts.cartIds, orders.createdAt, orders.updatedAt))
                .from(orders)
                .where(condition(userId, dpId, status))
                .offset(pageOption.getPage() * pageOption.getSize())
                .limit(pageOption.getSize())
                .orderBy(sort(pageOption.getDirection()))
                .fetch();
    }

    @Override
	public Long countWithCondition(Long userId, Long dpId, OrderStatus status) {
        return queryFactory
                .select(orders.count())
                .from(orders)
                .where(condition(userId, dpId, status))
                .fetchOne();
    }

    private BooleanExpression condition(Long userId, Long dpId, OrderStatus status) {
        BooleanExpression ex = null;

        if (status != null) {
            ex = orders.status.eq(status);

            if (dpId != null) {
                ex.and(isProduct(dpId));
            }

            if (userId != null) {
                ex.and(isUser(userId));
            }

            return ex;
        }

        if (dpId != null) {
            ex = isProduct(dpId);
        }

        if (userId != null) {
            if (ex == null) {
                ex = isUser(userId);
            } else {
                ex = ex.and(isUser(userId));
            }

            return ex;
        }

        return null;
    }

    private BooleanExpression isProduct(Long dpId) {
        return displayProducts.id.eq(dpId);
    }

    private BooleanExpression isUser(Long userId) {
        return orders.users.id.eq(userId);
    }

    private OrderSpecifier<LocalDateTime> sort(Direction direction) {
        if (direction == null) {
            return orders.createdAt.desc();
        }
        if (direction.isAscending()) {
            return orders.createdAt.asc();
        }
        return orders.createdAt.desc();
    }
}
