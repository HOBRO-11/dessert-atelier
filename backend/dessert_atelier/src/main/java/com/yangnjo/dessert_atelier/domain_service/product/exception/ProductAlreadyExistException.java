package com.yangnjo.dessert_atelier.domain_service.product.exception;

public class ProductAlreadyExistException extends RuntimeException{
  public ProductAlreadyExistException() {
    super();
  }

  public ProductAlreadyExistException(Throwable ex) {
    super(ex);
  }

  public ProductAlreadyExistException(String message) {
    super(message);
  }

  public ProductAlreadyExistException(String message, Throwable ex) {
    super(message, ex);
  }
}
