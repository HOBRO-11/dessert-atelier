package com.yangnjo.dessert_atelier.db.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    DISCOUNT("DISCOUNT"),
    SALE("SALE"),
    SOLD_OUT("SOLD_OUT"),
    HIDE("HIDE");

    private final String status;
}
