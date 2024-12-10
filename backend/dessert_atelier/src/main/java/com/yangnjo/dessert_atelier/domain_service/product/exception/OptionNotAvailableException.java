package com.yangnjo.dessert_atelier.domain_service.product.exception;

public class OptionNotAvailableException extends RuntimeException {
  public OptionNotAvailableException() {
    super();
  }

  public OptionNotAvailableException(Throwable ex) {
    super(ex);
  }

  public OptionNotAvailableException(String message) {
    super(message);
  }

  public OptionNotAvailableException(String message, Throwable ex) {
    super(message, ex);
  }

}
