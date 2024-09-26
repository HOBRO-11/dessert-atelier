package com.yangnjo.dessert_atelier.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QnAStatus {
    
    WAITING("WAITING"),
    HIDE("HIDE"),
    PUB("PUBLIC");

    private final String status;
}
