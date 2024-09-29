package com.yangnjo.dessert_atelier.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SalePolicyStatus {
    DISCOUNT("DISCOUNT"),
    NORMAL("NORMAL");

    public final String policy;
}
