package com.yangnjo.dessert_atelier.domain_model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DisplayProductStatus {
    PREPARE,
    HIDE,
    ON_SALE,
    SOLD_OUT;

}
