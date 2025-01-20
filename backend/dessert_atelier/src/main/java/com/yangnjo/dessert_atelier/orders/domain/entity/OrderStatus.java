package com.yangnjo.dessert_atelier.orders.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PENDING_PAYMENT,
    PAYMENT_SUCCESS,
    REQUEST_CANCEL,
    CANCEL,
    ORDER_CONFIRM,
    REQUEST_REFUND,
    REFUND,
    HIDE;

}
