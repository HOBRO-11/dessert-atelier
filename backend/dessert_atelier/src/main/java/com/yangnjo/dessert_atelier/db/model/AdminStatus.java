package com.yangnjo.dessert_atelier.db.model;

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
