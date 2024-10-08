package com.yangnjo.dessert_atelier.domain.display_product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SaleStatus {
    HIDE,
    ON_SALE,
    SOLD_OUT,
    DISCOUNT;

}
