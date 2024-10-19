package com.yangnjo.dessert_atelier.domain_service.member.exception;

public class AddressNotFoundException extends RuntimeException {
  public AddressNotFoundException() {
    super();
  }

  public AddressNotFoundException(Throwable ex) {
    super(ex);
  }

  public AddressNotFoundException(String message) {
    super(message);
  }

  public AddressNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
