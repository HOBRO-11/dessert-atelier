package com.yangnjo.dessert_atelier.repository.total.dto;

import java.time.LocalDate;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.total.QTotalSaleProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalSaleProductGraphDto {

    private Long id;
    private Long productId;
    private Integer saleAmount;
    private LocalDate createdAt;

    public static Expression<TotalSaleProductGraphDto> asDto() {
        QTotalSaleProduct totalSaleProduct = QTotalSaleProduct.totalSaleProduct;
        return Projections.constructor(
                TotalSaleProductGraphDto.class,
                totalSaleProduct.id,
                totalSaleProduct.product.id,
                totalSaleProduct.saleAmount,
                totalSaleProduct.createdAt);
    }

}
