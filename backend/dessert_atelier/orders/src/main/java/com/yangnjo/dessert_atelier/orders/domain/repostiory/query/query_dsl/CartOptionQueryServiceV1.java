package com.yangnjo.dessert_atelier.orders.domain.repostiory.query.query_dsl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.orders.domain.entity.QCartOption;
import com.yangnjo.dessert_atelier.orders.domain.repostiory.query.CartOptionQueryService;
import com.yangnjo.dessert_atelier.orders.dto.CartOptionDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CartOptionQueryServiceV1 implements CartOptionQueryService {

    QCartOption cartOption = QCartOption.cartOption;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<CartOptionDto> findByMemberId(Long memberId) {
        return Optional.ofNullable(queryFactory
                .select(CartOptionDto.asDto())
                .from(cartOption)
                .where(equalMemberId(memberId))
                .fetchOne());

    }

    private BooleanExpression equalMemberId(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId를 입력해주세요.");
        }
        return cartOption.memberId.eq(memberId);
    }
}
