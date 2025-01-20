package com.yangnjo.dessert_atelier.sale.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.sale.domain.entity.QShopPage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ShopPageDto {

    private Long id;
    private String naming;
    private List<Long> salePageIds;
    private boolean isDefault;
    private LocalDateTime startedDateAt;
    private LocalDateTime endedDateAt;
    private boolean isDeleteAtEndOfEvent;

    public static Expression<ShopPageDto> asDto() {
        QShopPage shopPage = QShopPage.shopPage;
        return Projections.constructor(ShopPageDto.class,
                shopPage.id,
                shopPage.naming,
                shopPage.salePageIds,
                shopPage.isDefault,
                shopPage.startedDateAt,
                shopPage.endedDateAt,
                shopPage.isDeleteAtEndOfEvent);
    }

}
