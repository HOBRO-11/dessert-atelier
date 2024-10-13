package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.display_product.QDisplayProductPreset.*;

import java.time.LocalDateTime;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DppSimpleDto {
    private Long id;
    private String naming;
    private String thumb;
    private String title;
    private Integer price;
    private Integer percentDiscount;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private boolean isDefault;
    
    public static Expression<DppSimpleDto> asDto(){
        return Projections.constructor(DppSimpleDto.class,
                displayProductPreset.id,
                displayProductPreset.naming,
                displayProductPreset.thumb,
                displayProductPreset.title,
                displayProductPreset.price,
                displayProductPreset.percentDiscount,
                displayProductPreset.startDateTime,
                displayProductPreset.endDateTime, displayProductPreset.isDefault);
    }
}
