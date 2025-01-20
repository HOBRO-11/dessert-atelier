package com.yangnjo.dessert_atelier.sale.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOption;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeader;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaleOptionCreateDto {
    Long saleOptionHeaderId;
    String explanation;
    Integer price;
    List<ProductQuantityCreateDto> productQuantities;

    public SaleOption toEntity(SaleOptionHeader oh) {
        return SaleOption.builder()
                .saleOptionHeader(oh)
                .saleOptionStatus(SaleOptionStatus.AVAILABLE)
                .explanation(explanation)
                .price(price).build();
    }

}
