package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.display_product.QDisplayProductPreset.*;

import java.time.LocalDateTime;
import java.util.Map;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DppDto {

    private Long id;
    private String thumb;
    private String title;
    private Integer price;
    private Integer optionLayer;
    private String content;
    private Map<String, String> dppImgMap;
    private Integer percentDiscount;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public static Expression<DppDto> asDto() {
        return Projections.constructor(DppDto.class,
                displayProductPreset.id,
                displayProductPreset.thumb,
                displayProductPreset.title,
                displayProductPreset.price,
                displayProductPreset.optionLayer,
                displayProductPreset.content,
                displayProductPreset.dppImg.imageUrls,
                displayProductPreset.percentDiscount,
                displayProductPreset.startDateTime,
                displayProductPreset.endDateTime);
    }
}
