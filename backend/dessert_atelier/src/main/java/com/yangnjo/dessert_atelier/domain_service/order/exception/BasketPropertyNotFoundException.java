package com.yangnjo.dessert_atelier.domain_service.order.exception;

public class BasketPropertyNotFoundException extends RuntimeException {
  public BasketPropertyNotFoundException() {
    super();
  }

  public BasketPropertyNotFoundException(Throwable ex) {
    super(ex);
  }

  public BasketPropertyNotFoundException(String message) {
    super(message);
  }

  public BasketPropertyNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
