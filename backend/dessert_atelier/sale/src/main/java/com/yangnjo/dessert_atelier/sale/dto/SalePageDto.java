package com.yangnjo.dessert_atelier.sale.dto;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.sale.domain.entity.QSalePage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SalePageDto {
    private Long id;
    private String title;
    private List<String> subtitle;
    private List<String> thumbnail;
    private List<String> content;
    private Long productReactId;
    private List<Long> optionHeaderIds;

    public static Expression<SalePageDto> asDto() {
        QSalePage salePage = QSalePage.salePage;
        return Projections.constructor(SalePageDto.class,
                salePage.id,
                salePage.title,
                salePage.subtitles,
                salePage.content,                
                salePage.productReactId,
                salePage.optionHeaderIds,
                salePage.thumbnail,
                salePage.content);
    }
}
