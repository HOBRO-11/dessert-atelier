package com.yangnjo.dessert_atelier.repository.total.dto;

import java.time.LocalDate;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.total.QTotalSaleOption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalSaleOptionDto {

    private Long id;
    private Long optionId;
    private String description;
    private Integer saleAmount;
    private LocalDate createdAt;

    public static Expression<TotalSaleOptionDto> asDto() {
        QTotalSaleOption totalSaleOption = QTotalSaleOption.totalSaleOption;
        return Projections.constructor(
                TotalSaleOptionDto.class,
                totalSaleOption.id,
                totalSaleOption.option.id,
                totalSaleOption.option.description,
                totalSaleOption.saleAmount,
                totalSaleOption.createdAt);
    }

}
