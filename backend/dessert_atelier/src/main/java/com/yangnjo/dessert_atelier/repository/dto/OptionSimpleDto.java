package com.yangnjo.dessert_atelier.repository.dto;

import com.yangnjo.dessert_atelier.domain.OptionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionSimpleDto {

    private Long id;

    private String description;

    private OptionStatus status;

    private int price;

}
