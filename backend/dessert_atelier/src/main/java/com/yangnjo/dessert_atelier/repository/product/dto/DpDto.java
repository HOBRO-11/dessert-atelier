package com.yangnjo.dessert_atelier.repository.product.dto;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.model.ImageSrc;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.domain_model.product.QDisplayProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DpDto {
    private Long id;
    private String title;
    private String thumb;
    private Integer optionLayer;
    private String description;
    private List<ImageSrc> images;
    private DisplayProductStatus dpStatus;

    public static Expression<DpDto> asDto() {
        QDisplayProduct displayProduct = QDisplayProduct.displayProduct;
        return Projections.constructor(DpDto.class,
                displayProduct.id,
                displayProduct.title,
                displayProduct.thumb,
                displayProduct.optionLayer,
                displayProduct.description,
                displayProduct.images,
                displayProduct.displayProductStatus);
    }
}
