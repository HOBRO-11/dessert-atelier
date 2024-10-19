package com.yangnjo.dessert_atelier.domain_service.delivery.exception;

public class DeliveryNotFoundException extends RuntimeException {
  public DeliveryNotFoundException() {
    super();
  }

  public DeliveryNotFoundException(Throwable ex) {
    super(ex);
  }

  public DeliveryNotFoundException(String message) {
    super(message);
  }

  public DeliveryNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
