package com.yangnjo.dessert_atelier.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminRole {

    NONE("NONE"),
    OWNER("OWNER"),
    STAFF("STAFF"),
    PART_TIME("PART_TIME");

    private final String role;
}
