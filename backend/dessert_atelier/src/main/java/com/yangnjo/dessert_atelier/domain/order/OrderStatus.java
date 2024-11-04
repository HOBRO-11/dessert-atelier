package com.yangnjo.dessert_atelier.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PAYMENT_IN_PROGRESS,
    PAYMENT_COMPLETED,
    REQUEST_CANCEL,
    CANCEL,
    COMPLETED,
    REQUEST_REFUND,
    REFUND,
    HIDE;

}
