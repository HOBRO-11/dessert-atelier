package com.yangnjo.dessert_atelier.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    BAN("BAN"),
    ACTIVE("ACTIVE");

    private final String status;
}
