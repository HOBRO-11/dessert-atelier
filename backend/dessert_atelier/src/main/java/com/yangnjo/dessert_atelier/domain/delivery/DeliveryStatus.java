package com.yangnjo.dessert_atelier.domain.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {

  COMPLETED,
  IN_PROGRESS,
  PREPARING,
  REQUEST,
  CANCEL,
  REFUND,
  HIDE;

}
