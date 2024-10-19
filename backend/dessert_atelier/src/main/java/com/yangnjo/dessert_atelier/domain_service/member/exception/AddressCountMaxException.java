package com.yangnjo.dessert_atelier.domain_service.member.exception;

public class AddressCountMaxException extends RuntimeException {

  public AddressCountMaxException() {
    super();
  }

  public AddressCountMaxException(Throwable ex) {
    super(ex);
  }

  public AddressCountMaxException(String message) {
    super(message);
  }

  public AddressCountMaxException(String message, Throwable ex) {
    super(message, ex);
  }
}
