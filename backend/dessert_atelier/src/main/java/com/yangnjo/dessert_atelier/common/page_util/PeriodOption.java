package com.yangnjo.dessert_atelier.common.page_util;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PeriodOption {
    private LocalDateTime start;
    private LocalDateTime end;

}
