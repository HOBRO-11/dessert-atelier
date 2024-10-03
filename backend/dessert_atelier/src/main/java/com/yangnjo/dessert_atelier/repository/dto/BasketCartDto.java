package com.yangnjo.dessert_atelier.repository.dto;

import com.yangnjo.dessert_atelier.domain.DisplayProductStatus;
import com.yangnjo.dessert_atelier.domain.OptionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasketCartDto {

    private Long id;

    private Long displayProductId;
    
    private String title;

    private String thumb;

    private DisplayProductStatus displayProductStatus;

    private Long optionId;

    private String optionDescription;

    private int optionPrice;

    private int quantity;

    private OptionStatus optionStatus;

}
