package com.yangnjo.dessert_atelier.domain_service.product.exception;

public class ComponentNotFoundException extends RuntimeException {
  public ComponentNotFoundException() {
    super();
  }

  public ComponentNotFoundException(Throwable ex) {
    super(ex);
  }

  public ComponentNotFoundException(String message) {
    super(message);
  }

  public ComponentNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
