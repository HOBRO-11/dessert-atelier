package com.yangnjo.dessert_atelier.repository.total.dto;

import java.time.LocalDate;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.total.QTotalSaleProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalSaleProductDto {

    private Long id;
    private Long productId;
    private String productName;
    private Integer saleAmount;
    private LocalDate createdAt;

    public static Expression<TotalSaleProductDto> asDto() {
        QTotalSaleProduct totalSaleProduct = QTotalSaleProduct.totalSaleProduct;
        return Projections.constructor(
                TotalSaleProductDto.class,
                totalSaleProduct.id,
                totalSaleProduct.product.id,
                totalSaleProduct.product.name,
                totalSaleProduct.saleAmount,
                totalSaleProduct.createdAt);
    }

}
