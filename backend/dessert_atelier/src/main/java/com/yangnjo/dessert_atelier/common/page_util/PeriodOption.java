package com.yangnjo.dessert_atelier.common.page_util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PeriodOption {
    private LocalDateTime start;
    private LocalDateTime end;

    public static BooleanExpression betweenLD(DateTimePath<LocalDate> criteria, PeriodOption periodOption) {
        return periodOption == null ? null
                : criteria.between(periodOption.getStart().toLocalDate(),
                        periodOption.getEnd().toLocalDate());
    }

    public static BooleanExpression betweenLDT(DateTimePath<LocalDateTime> criteria, PeriodOption periodOption) {
        return periodOption == null ? null
                : criteria.between(periodOption.getStart(),
                        periodOption.getEnd());
    }
}
