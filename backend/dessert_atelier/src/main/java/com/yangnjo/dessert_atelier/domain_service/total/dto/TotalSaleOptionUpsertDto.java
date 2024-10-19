package com.yangnjo.dessert_atelier.domain_service.total.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalSaleOptionUpsertDto {
  LocalDate date;
  Long optionId;
  Integer saleAmount;
}
