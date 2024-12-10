package com.yangnjo.dessert_atelier.domain_model.react;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QnAStatus {
    
    WAITING,
    ANSWERED,
    HIDE,
    PUB;
}
