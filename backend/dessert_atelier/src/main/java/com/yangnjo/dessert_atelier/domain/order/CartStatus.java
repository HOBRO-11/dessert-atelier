package com.yangnjo.dessert_atelier.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CartStatus {

    WAITING,
    USED;

}