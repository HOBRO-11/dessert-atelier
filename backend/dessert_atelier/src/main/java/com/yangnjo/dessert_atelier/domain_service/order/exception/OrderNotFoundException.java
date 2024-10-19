package com.yangnjo.dessert_atelier.domain_service.order.exception;

public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException() {
    super();
  }

  public OrderNotFoundException(Throwable ex) {
    super(ex);
  }

  public OrderNotFoundException(String message) {
    super(message);
  }

  public OrderNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
