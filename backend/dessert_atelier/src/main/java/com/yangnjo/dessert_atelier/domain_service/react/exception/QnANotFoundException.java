package com.yangnjo.dessert_atelier.domain_service.react.exception;

public class QnANotFoundException extends RuntimeException {
  public QnANotFoundException() {
    super();
  }

  public QnANotFoundException(Throwable ex) {
    super(ex);
  }

  public QnANotFoundException(String message) {
    super(message);
  }

  public QnANotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
