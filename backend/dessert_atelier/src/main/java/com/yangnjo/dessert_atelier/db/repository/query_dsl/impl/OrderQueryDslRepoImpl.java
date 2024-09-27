package com.yangnjo.dessert_atelier.db.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.constructor;
import static com.yangnjo.dessert_atelier.db.entity.QOrders.orders;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.model.OrderStatus;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.OrderQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderQueryDslRepoImpl implements OrderQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderDto> findByCondition(int page, int size, Users user, OrderStatus status, Direction direction) {
        return queryFactory
                .select(constructor(OrderDto.class, orders.code, orders.users, orders.carts, orders.addresses,
                        orders.status, orders.createdAt))
                .from(orders)
                .where(isUser(user).and(isStatus(status)))
                .offset(page * size)
                .limit(size)
                .orderBy(sort(direction))
                .fetch();
    }

    @Override
    public Long countFindByCondition(Users user, OrderStatus status) {
        return queryFactory
                .select(orders.count())
                .from(orders)
                .where(isUser(user).and(isStatus(status)))
                .fetchOne();
    }

    private BooleanExpression isStatus(OrderStatus status) {
        return status == null ? null : orders.status.eq(status);
    }

    private BooleanExpression isUser(Users user) {
        return user == null ? null : orders.users.eq(user);
    }

    private OrderSpecifier<LocalDateTime> sort(Direction direction) {
        if (direction.isAscending()) {
            return orders.createdAt.asc();
        }
        return orders.createdAt.desc();
    }

}
