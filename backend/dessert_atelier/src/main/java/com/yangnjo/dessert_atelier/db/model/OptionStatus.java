package com.yangnjo.dessert_atelier.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OptionStatus {
    AVAILABLE("사용 가능"),
    UNAVAILABLE("사용 불가능");

    private final String status;
}
