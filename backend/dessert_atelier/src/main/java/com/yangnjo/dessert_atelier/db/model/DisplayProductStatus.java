package com.yangnjo.dessert_atelier.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DisplayProductStatus {

    DISCOUNT("DISCOUNT"),
    SALE("SALE"),
    SOLD_OUT("SOLD_OUT"),
    HIDE("HIDE");

    private final String status;
}
