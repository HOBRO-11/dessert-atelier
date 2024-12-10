package com.yangnjo.dessert_atelier.domain_service.product.exception;

public class PresetTableNotFoundException extends RuntimeException {
  public PresetTableNotFoundException() {
    super();
  }

  public PresetTableNotFoundException(Throwable ex) {
    super(ex);
  }

  public PresetTableNotFoundException(String message) {
    super(message);
  }

  public PresetTableNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
