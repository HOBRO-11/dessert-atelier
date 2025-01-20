package com.yangnjo.dessert_atelier.orders.exception;

public class CartOptionMaxCountException extends RuntimeException {
  public CartOptionMaxCountException() {
    super();
  }

  public CartOptionMaxCountException(Throwable ex) {
    super(ex);
  }

  public CartOptionMaxCountException(String message) {
    super(message);
  }

  public CartOptionMaxCountException(String message, Throwable ex) {
    super(message, ex);
  }
}
