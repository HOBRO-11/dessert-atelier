package com.yangnjo.dessert_atelier.domain_model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderedOptionStatus {
    NONE, REQUEST_REFUND, REFUND;
}
