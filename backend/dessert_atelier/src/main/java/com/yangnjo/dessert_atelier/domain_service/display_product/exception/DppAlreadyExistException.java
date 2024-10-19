package com.yangnjo.dessert_atelier.domain_service.display_product.exception;

public class DppAlreadyExistException extends RuntimeException {
  public DppAlreadyExistException() {
    super();
  }

  public DppAlreadyExistException(Throwable ex) {
    super(ex);
  }

  public DppAlreadyExistException(String message) {
    super(message);
  }

  public DppAlreadyExistException(String message, Throwable ex) {
    super(message, ex);
  }
}
