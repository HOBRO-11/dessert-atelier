package com.yangnjo.dessert_atelier.domain_service.react.exception;

public class QnANonAuthException extends RuntimeException {

  public QnANonAuthException() {
    super();
  }

  public QnANonAuthException(Throwable ex) {
    super(ex);
  }

  public QnANonAuthException(String message) {
    super(message);
  }

  public QnANonAuthException(String message, Throwable ex) {
    super(message, ex);
  }

}
