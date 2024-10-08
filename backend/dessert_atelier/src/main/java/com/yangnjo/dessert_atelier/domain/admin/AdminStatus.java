package com.yangnjo.dessert_atelier.domain.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminStatus {

    PENDING,
    ACTIVE,
    BAN;

}
