package com.yangnjo.dessert_atelier.sale.dto;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.sale.domain.entity.QSalePage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SalePageSimpleDto {
    private Long id;
    private String title;
    private List<String> thumb;
    private List<String> subtitles;

    public static Expression<SalePageSimpleDto> asDto() {
        QSalePage salePage = QSalePage.salePage;
        return Projections.constructor(SalePageSimpleDto.class,
                salePage.id,
                salePage.title,
                salePage.thumbnail,
                salePage.subtitles);
    }
}
