package com.yangnjo.dessert_atelier.domain_service.product.exception;

public class OptionNotFoundException extends RuntimeException {
  public OptionNotFoundException() {
    super();
  }

  public OptionNotFoundException(Throwable ex) {
    super(ex);
  }

  public OptionNotFoundException(String message) {
    super(message);
  }

  public OptionNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
