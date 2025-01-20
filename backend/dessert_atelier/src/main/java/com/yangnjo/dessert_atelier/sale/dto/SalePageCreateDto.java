package com.yangnjo.dessert_atelier.sale.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.sale.domain.entity.SalePage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SalePageCreateDto {
    String title;
    List<String> subtitle;
    List<String> thumbnail;
    List<String> content;

    public SalePage toEntity() {
        return SalePage.builder().title(title).subtitles(subtitle).thumbnail(thumbnail).content(content).build();
    }
}
