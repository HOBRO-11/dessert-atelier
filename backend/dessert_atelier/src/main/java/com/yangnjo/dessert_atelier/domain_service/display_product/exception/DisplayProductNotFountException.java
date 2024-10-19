package com.yangnjo.dessert_atelier.domain_service.display_product.exception;

public class DisplayProductNotFountException extends RuntimeException {
  public DisplayProductNotFountException() {
    super();
  }

  public DisplayProductNotFountException(Throwable ex) {
    super(ex);
  }

  public DisplayProductNotFountException(String message) {
    super(message);
  }

  public DisplayProductNotFountException(String message, Throwable ex) {
    super(message, ex);
  }

}
