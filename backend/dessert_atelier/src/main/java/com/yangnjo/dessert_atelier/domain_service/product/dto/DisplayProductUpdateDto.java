package com.yangnjo.dessert_atelier.domain_service.product.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplayProductUpdateDto {
    Long dpId;
    String title;
    List<String> thumb;
    String desc;
    Integer optionLayer;
}
