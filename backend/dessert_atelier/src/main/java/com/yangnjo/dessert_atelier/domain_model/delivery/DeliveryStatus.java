package com.yangnjo.dessert_atelier.domain_model.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {

    PREPARING,
    REQUEST,
    COMPLETED,
    CANCEL,
    REFUND,
    HIDE;

}
