package com.yangnjo.dessert_atelier.domain_service.order.exception;

public class BasketOverMaxCountException extends RuntimeException {
  public BasketOverMaxCountException() {
    super();
  }

  public BasketOverMaxCountException(Throwable ex) {
    super(ex);
  }

  public BasketOverMaxCountException(String message) {
    super(message);
  }

  public BasketOverMaxCountException(String message, Throwable ex) {
    super(message, ex);
  }
}
