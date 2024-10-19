package com.yangnjo.dessert_atelier.domain_service.order.exception;

public class OptionQuantityNotFoundException extends RuntimeException {
  public OptionQuantityNotFoundException() {
    super();
  }

  public OptionQuantityNotFoundException(Throwable ex) {
    super(ex);
  }

  public OptionQuantityNotFoundException(String message) {
    super(message);
  }

  public OptionQuantityNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }
}
