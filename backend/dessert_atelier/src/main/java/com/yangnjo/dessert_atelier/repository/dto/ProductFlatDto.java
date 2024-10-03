package com.yangnjo.dessert_atelier.repository.dto;

import com.yangnjo.dessert_atelier.domain.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductFlatDto {

    private Long id;

    private String name;

    private ProductStatus status;

    private int price;

    private String thumb;
}
