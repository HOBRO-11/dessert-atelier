package com.yangnjo.dessert_atelier.domain_service.order.exception;

public class OrderCodeGenerateFailedException extends RuntimeException {
  public OrderCodeGenerateFailedException() {
    super();
  }

  public OrderCodeGenerateFailedException(Throwable ex) {
    super(ex);
  }

  public OrderCodeGenerateFailedException(String message) {
    super(message);
  }

  public OrderCodeGenerateFailedException(String message, Throwable ex) {
    super(message, ex);
  }

}
