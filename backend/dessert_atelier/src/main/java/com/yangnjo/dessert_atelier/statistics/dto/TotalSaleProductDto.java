package com.yangnjo.dessert_atelier.statistics.dto;

import java.time.LocalDate;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.statistics.domain.entity.QTotalSaleProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalSaleProductDto {

    private Long id;
    private Long productId;
    private Integer saleAmount;
    private LocalDate createdAt;

    public static Expression<TotalSaleProductDto> asDto() {
        QTotalSaleProduct totalSaleProduct = QTotalSaleProduct.totalSaleProduct;
        return Projections.constructor(
                TotalSaleProductDto.class,
                totalSaleProduct.id,
                totalSaleProduct.productId,
                totalSaleProduct.saleAmount,
                totalSaleProduct.createdAt);
    }

}
