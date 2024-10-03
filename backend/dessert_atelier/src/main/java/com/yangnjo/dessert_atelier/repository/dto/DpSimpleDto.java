package com.yangnjo.dessert_atelier.repository.dto;

import com.yangnjo.dessert_atelier.domain.DisplayProductStatus;
import com.yangnjo.dessert_atelier.domain.SalePolicyStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DpSimpleDto {

    private Long id;

    private String title;
    
    private int price;

    private String thumb;

    private SalePolicyStatus salePolicyStatus;

    private DisplayProductStatus displayProductStatus;
}
