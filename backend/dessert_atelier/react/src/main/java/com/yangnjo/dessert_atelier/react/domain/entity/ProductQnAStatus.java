package com.yangnjo.dessert_atelier.react.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductQnAStatus {
    
    REQ,
    RESP,
    MEMBER_HIDE,
    ADMIN_HIDE;
}
