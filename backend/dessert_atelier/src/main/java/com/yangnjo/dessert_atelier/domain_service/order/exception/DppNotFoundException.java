package com.yangnjo.dessert_atelier.domain_service.order.exception;

public class DppNotFoundException extends RuntimeException {
  public DppNotFoundException() {
    super();
  }

  public DppNotFoundException(Throwable ex) {
    super(ex);
  }

  public DppNotFoundException(String message) {
    super(message);
  }

  public DppNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
