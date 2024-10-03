package com.yangnjo.dessert_atelier.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.*;
import static com.yangnjo.dessert_atelier.domain.QTodos.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.TodoStatus;
import com.yangnjo.dessert_atelier.repository.dto.DateOption;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.TodoOrderCartsDto;
import com.yangnjo.dessert_atelier.repository.dto.TodoSimpleDto;
import com.yangnjo.dessert_atelier.repository.query_dsl.TodoQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TodoQueryDslRepoImpl implements TodoQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
	public List<TodoSimpleDto> searchSimpleWithCondition(PageOption pageOption, DateOption dateOption,
            TodoStatus status) {
        return queryFactory
                .select(constructor(TodoSimpleDto.class, todos.id, todos.orders.code, todos.status,
                        todos.createdAt, todos.completeAt))
                .from(todos)
                .where(condition(dateOption, status))
                .offset(pageOption.getPage() * pageOption.getSize())
                .limit(pageOption.getSize())
                .orderBy(sort(pageOption.getDirection()))
                .fetch();
    }

    @Override
	public List<TodoOrderCartsDto> searchWithCondition(PageOption pageOption, DateOption dateOption,
            TodoStatus status) {
        return queryFactory
                .select(constructor(TodoOrderCartsDto.class, todos.id, todos.orders.code, todos.status,
                        todos.createdAt, todos.completeAt, todos.orders.orderCarts.cartIds))
                .from(todos)
                .where(condition(dateOption, status))
                .offset(pageOption.getPage() * pageOption.getSize())
                .limit(pageOption.getSize())
                .fetch();
    }

    @Override
	public Long countWithCondition(DateOption dateOption, TodoStatus status) {
        return queryFactory
                .select(todos.count())
                .from(todos)
                .where(condition(dateOption, status))
                .fetchOne();
    }

    private OrderSpecifier<?> sort(Direction direction) {
        if (direction == null) {
            return todos.id.desc();
        }
        if (direction.isAscending()) {
            return todos.id.asc();
        }
        return todos.id.desc();
    }

    private BooleanExpression condition(DateOption dateOption, TodoStatus status) {
        if (dateOption == null) {
            if (status == null) {
                return null;
            }

            return todos.status.eq(status);
        }

        if (dateOption.getStartDate() == null) {
            LocalDateTime sixMonthsAgo = dateOption.getEndDate().minusMonths(6);
            return todos.createdAt.between(sixMonthsAgo, dateOption.getEndDate());
        }

        if (dateOption.getEndDate() == null) {
            return todos.createdAt.between(dateOption.getStartDate(), LocalDateTime.now());
        }

        return todos.createdAt.between(dateOption.getStartDate(), dateOption.getEndDate());
    }
}
