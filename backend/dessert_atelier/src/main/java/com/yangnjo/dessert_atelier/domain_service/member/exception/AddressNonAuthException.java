package com.yangnjo.dessert_atelier.domain_service.member.exception;

public class AddressNonAuthException extends RuntimeException {

  public AddressNonAuthException() {
    super();
  }

  public AddressNonAuthException(Throwable ex) {
    super(ex);
  }

  public AddressNonAuthException(String message) {
    super(message);
  }

  public AddressNonAuthException(String message, Throwable ex) {
    super(message, ex);
  }

}
