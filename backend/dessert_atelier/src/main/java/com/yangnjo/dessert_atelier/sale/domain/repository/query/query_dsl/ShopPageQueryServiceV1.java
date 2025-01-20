package com.yangnjo.dessert_atelier.sale.domain.repository.query.query_dsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.sale.domain.entity.QShopPage;
import com.yangnjo.dessert_atelier.sale.domain.repository.query.ShopPageQueryService;
import com.yangnjo.dessert_atelier.sale.dto.ShopPageDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShopPageQueryServiceV1 implements ShopPageQueryService {

    private final JPAQueryFactory queryFactory;
    QShopPage shopPage = QShopPage.shopPage;

    @Override
    public List<ShopPageDto> findAll() {
        return queryFactory.select(ShopPageDto.asDto())
                .from(shopPage)
                .orderBy(byId())
                .fetch();
    }

    private OrderSpecifier<Long> byId() {
        return shopPage.id.asc();
    }
}
