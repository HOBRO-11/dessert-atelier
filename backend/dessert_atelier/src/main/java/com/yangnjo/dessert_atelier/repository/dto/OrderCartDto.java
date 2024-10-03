package com.yangnjo.dessert_atelier.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCartDto {
    private Long id;

    private Long displayProductId;
    
    private String title;

    private String thumb;

    private Long optionId;

    private String optionDescription;

    private int optionPrice;

    private int quantity;

}
