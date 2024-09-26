package com.yangnjo.dessert_atelier.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CartStatus {

    WAITING("WAITING"),
    USED("USED");

    private final String status;
}
