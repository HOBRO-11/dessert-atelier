package com.yangnjo.dessert_atelier.orders.exception;

public class OrderIdGenerateFailedException extends RuntimeException {
  public OrderIdGenerateFailedException() {
    super();
  }

  public OrderIdGenerateFailedException(Throwable ex) {
    super(ex);
  }

  public OrderIdGenerateFailedException(String message) {
    super(message);
  }

  public OrderIdGenerateFailedException(String message, Throwable ex) {
    super(message, ex);
  }

}
