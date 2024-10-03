package com.yangnjo.dessert_atelier.repository.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DateOption {

    private LocalDateTime startDate;

    private LocalDateTime endDate;
    
}
