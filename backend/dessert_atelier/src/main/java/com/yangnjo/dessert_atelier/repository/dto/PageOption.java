package com.yangnjo.dessert_atelier.repository.dto;

import org.springframework.data.domain.Sort.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageOption {

    private int page;

    private int size;

    private Direction direction;
    
}
