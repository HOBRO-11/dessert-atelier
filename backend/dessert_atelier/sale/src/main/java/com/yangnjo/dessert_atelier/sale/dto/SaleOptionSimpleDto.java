package com.yangnjo.dessert_atelier.sale.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.sale.domain.entity.QSaleOption;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaleOptionSimpleDto {
    private Long id;
    private Long optionHeaderId;
    private SaleOptionStatus optionStatus;
    private String description;
    private Integer price;

    public static Expression<SaleOptionSimpleDto> asDto() {
        QSaleOption saleOption = QSaleOption.saleOption;
        return Projections.constructor(SaleOptionSimpleDto.class,
                saleOption.id,
                saleOption.saleOptionHeader.id,
                saleOption.status,
                saleOption.explanation,
                saleOption.price);
    }
}
