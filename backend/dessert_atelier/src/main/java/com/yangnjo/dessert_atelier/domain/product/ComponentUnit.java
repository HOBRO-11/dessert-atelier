package com.yangnjo.dessert_atelier.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ComponentUnit {
    GRAM("g"),
    MILLILITER("ml"),
    COUNT("개");

    private final String unit;
}
