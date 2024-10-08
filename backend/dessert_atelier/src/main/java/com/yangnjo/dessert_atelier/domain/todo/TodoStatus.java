package com.yangnjo.dessert_atelier.domain.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED;

}
