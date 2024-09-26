package com.yangnjo.dessert_atelier.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    SHOPPING_BASKET("SHOPPING_BASKET"),
    PAYMENT_COMPLETED("PAYMENT_COMPLETED"),
    REQUEST_CANCEL("REQUEST_CANCEL"),
    CANCEL("CANCEL"),
    COMPLETED("COMPLETED"),
    REFUND("REFUND"),
    HIDE("HIDE");

    private final String title;

}
