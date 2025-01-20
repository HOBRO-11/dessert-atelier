package com.yangnjo.dessert_atelier.sale.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.sale.domain.entity.QSaleOptionHeader;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeaderType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class saleOptionHeaderDto {
    private Long id;
    private Long salePageId;
    private String header;
    private SaleOptionHeaderType headerType;

    public static Expression<saleOptionHeaderDto> asDto() {
        QSaleOptionHeader productOptionHeader = QSaleOptionHeader.saleOptionHeader;
        return Projections.constructor(saleOptionHeaderDto.class,
                productOptionHeader.id,
                productOptionHeader.salePage.id,
                productOptionHeader.header,
                productOptionHeader.headerType);
    }
}
