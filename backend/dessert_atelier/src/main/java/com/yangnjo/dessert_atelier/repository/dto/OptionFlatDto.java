package com.yangnjo.dessert_atelier.repository.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain.OptionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionFlatDto {

    private Long id;

    private Long displayProductId;

    private String description;
    
    private int totalQuantity;

    private OptionStatus status;

    private int price;

    private List<Long> pqIds;

}
