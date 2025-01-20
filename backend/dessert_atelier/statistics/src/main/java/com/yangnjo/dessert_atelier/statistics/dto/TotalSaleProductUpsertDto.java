package com.yangnjo.dessert_atelier.statistics.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalSaleProductUpsertDto {
    LocalDate date;
    Long productId;
    Integer saleAmount;
}
