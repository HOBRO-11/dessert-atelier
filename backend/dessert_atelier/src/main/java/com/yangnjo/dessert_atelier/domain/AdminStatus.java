package com.yangnjo.dessert_atelier.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminStatus {

    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    BAN("BAN");

    private final String status;
}
