package com.yangnjo.dessert_atelier.domain_service.product.exception;

public class DisplayProductAlreadyExistException extends RuntimeException {
  public DisplayProductAlreadyExistException() {
    super();
  }

  public DisplayProductAlreadyExistException(Throwable ex) {
    super(ex);
  }

  public DisplayProductAlreadyExistException(String message) {
    super(message);
  }

  public DisplayProductAlreadyExistException(String message, Throwable ex) {
    super(message, ex);
  }

}
