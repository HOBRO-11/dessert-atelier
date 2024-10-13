package com.yangnjo.dessert_atelier.repository;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PeriodOption {
    private LocalDateTime start;
    private LocalDateTime end;

}
