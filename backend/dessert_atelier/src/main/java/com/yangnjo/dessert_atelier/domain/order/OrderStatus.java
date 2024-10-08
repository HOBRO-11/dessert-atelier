package com.yangnjo.dessert_atelier.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    SHOPPING_BASKET,
    PAYMENT_COMPLETED,
    REQUEST_CANCEL,
    CANCEL,
    COMPLETED,
    REFUND,
    HIDE;

}
