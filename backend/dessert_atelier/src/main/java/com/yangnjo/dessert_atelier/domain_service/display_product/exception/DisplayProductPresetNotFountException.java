package com.yangnjo.dessert_atelier.domain_service.display_product.exception;

public class DisplayProductPresetNotFountException extends RuntimeException {
  public DisplayProductPresetNotFountException() {
    super();
  }

  public DisplayProductPresetNotFountException(Throwable ex) {
    super(ex);
  }

  public DisplayProductPresetNotFountException(String message) {
    super(message);
  }

  public DisplayProductPresetNotFountException(String message, Throwable ex) {
    super(message, ex);
  }

}
