package com.yangnjo.dessert_atelier.domain_service.product.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.model.ImageSrc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplayProductUpdateDto {
    Long dpId;
    String title;
    String thumb;
    String desc;
    Integer optionLayer;
    List<ImageSrc> images;
}
