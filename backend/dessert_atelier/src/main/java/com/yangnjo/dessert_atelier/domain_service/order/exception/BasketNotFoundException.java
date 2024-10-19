package com.yangnjo.dessert_atelier.domain_service.order.exception;

public class BasketNotFoundException extends RuntimeException {
  public BasketNotFoundException() {
    super();
  }

  public BasketNotFoundException(Throwable ex) {
    super(ex);
  }

  public BasketNotFoundException(String message) {
    super(message);
  }

  public BasketNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
