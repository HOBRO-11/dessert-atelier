package com.yangnjo.dessert_atelier.repository.order.query.query_dsl;

import java.util.Comparator;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain_model.order.BasketProperty;
import com.yangnjo.dessert_atelier.domain_model.order.QBasket;
import com.yangnjo.dessert_atelier.repository.order.dto.BasketDto;
import com.yangnjo.dessert_atelier.repository.order.query.BasketQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BasketQueryRepoImpl implements BasketQueryRepo {

    private final JPAQueryFactory queryFactory;
    QBasket basket = QBasket.basket;

    @Override
    public Optional<BasketDto> findByMemberId(Long memberId) {
        BasketDto dto = queryFactory
                .select(BasketDto.asDto())
                .from(basket)
                .where(equalMemberId(memberId))
                .fetchOne();

        if (dto == null) {
            return Optional.empty();
        }

        dto.getProperties().sort(intoLatest());

        return Optional.of(dto);
    }

    private BooleanExpression equalMemberId(Long memberId) {
        return basket.member.id.eq(memberId);
    }

    private Comparator<? super BasketProperty> intoLatest() {
        return (bp1, bp2) -> bp2.getUpdatedAt().compareTo(bp1.getUpdatedAt());
    }
}
