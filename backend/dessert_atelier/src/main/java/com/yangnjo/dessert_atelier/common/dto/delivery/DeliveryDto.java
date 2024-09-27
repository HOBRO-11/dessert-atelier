package com.yangnjo.dessert_atelier.common.dto.delivery;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryDto {
    
    private String code;

    private String companyName;

    private LocalDateTime createdAt;
}
