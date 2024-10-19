package com.yangnjo.dessert_atelier.domain_service.react.exception;

public class ReviewNotFoundException extends RuntimeException {
  public ReviewNotFoundException() {
    super();
  }

  public ReviewNotFoundException(Throwable ex) {
    super(ex);
  }

  public ReviewNotFoundException(String message) {
    super(message);
  }

  public ReviewNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
