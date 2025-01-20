package com.yangnjo.dessert_atelier.statistics.dto;

import java.time.LocalDate;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.statistics.domain.entity.QTotalSaleOption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalSaleOptionDto {

    private Long id;
    private Long optionId;
    private Integer saleAmount;
    private LocalDate createdAt;

    public static Expression<TotalSaleOptionDto> asDto() {
        QTotalSaleOption totalSaleOption = QTotalSaleOption.totalSaleOption;
        return Projections.constructor(
                TotalSaleOptionDto.class,
                totalSaleOption.id,
                totalSaleOption.optionId,
                totalSaleOption.saleAmount,
                totalSaleOption.createdAt);
    }

}
