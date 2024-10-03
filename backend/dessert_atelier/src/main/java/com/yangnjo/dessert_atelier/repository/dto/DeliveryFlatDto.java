package com.yangnjo.dessert_atelier.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryFlatDto {

    private String deliveryCode;

    private Long deliveryCompanyId;

    private String deliveryCompanyName;

    private String deliveryCompanyTel;

}
