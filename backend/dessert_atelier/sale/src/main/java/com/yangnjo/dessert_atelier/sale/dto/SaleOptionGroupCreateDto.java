package com.yangnjo.dessert_atelier.sale.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeader;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeaderType;
import com.yangnjo.dessert_atelier.sale.domain.entity.SalePage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaleOptionGroupCreateDto {

    private Long salePageId;
    private Integer numbering;
    private String header;
    private SaleOptionHeaderType headerType;
    List<SaleOptionCreateDto> saleOptions;

    public SaleOptionHeader toSaleOptionHeaderEntity(SalePage salePage) {
        return SaleOptionHeader.builder()
                .salePage(salePage)
                .header(header)
                .headerType(headerType)
                .build();
    }

}
