package com.yangnjo.dessert_atelier.domain_service.react.exception;

public class ReviewNonAuthException extends RuntimeException {

  public ReviewNonAuthException() {
    super();
  }

  public ReviewNonAuthException(Throwable ex) {
    super(ex);
  }

  public ReviewNonAuthException(String message) {
    super(message);
  }

  public ReviewNonAuthException(String message, Throwable ex) {
    super(message, ex);
  }

}
