package com.yangnjo.dessert_atelier.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.*;
import static com.yangnjo.dessert_atelier.domain.QCarts.*;

import java.util.Optional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.BasketCartDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderCartDto;
import com.yangnjo.dessert_atelier.repository.query_dsl.CartQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartQueryDslRepoImpl implements CartQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
	public Optional<OrderCartDto> searchOrderCartWithCondition(Long cartId) {
        return Optional.ofNullable(queryFactory
                .select(constructor(OrderCartDto.class, carts.id, carts.displayProducts.id, carts.displayProducts.title,
                        carts.displayProducts.thumb,
                        carts.options.id, carts.options.description, carts.options.price, carts.quantity))
                .from(carts)
                .where(isCart(cartId))
                .fetchOne());
    }

    @Override
	public Optional<BasketCartDto> searchBasketCartWithCondition(Long cartId) {
        return Optional.ofNullable(queryFactory
                .select(constructor(BasketCartDto.class, carts.id, carts.displayProducts.id,
                        carts.displayProducts.title,
                        carts.displayProducts.thumb,
                        carts.displayProducts.dpStatus,
                        carts.options.id, carts.options.description, carts.options.price, carts.quantity,
                        carts.options.status))
                .from(carts)
                .where(isCart(cartId))
                .fetchOne());
    }

    private BooleanExpression isCart(Long cartId) {
        return carts.id.eq(cartId);
    }

}