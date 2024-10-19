package com.yangnjo.dessert_atelier.domain_service.delivery.exception;

public class DeliveryCompanyNotFoundException extends RuntimeException {
  public DeliveryCompanyNotFoundException() {
    super();
  }

  public DeliveryCompanyNotFoundException(Throwable ex) {
    super(ex);
  }

  public DeliveryCompanyNotFoundException(String message) {
    super(message);
  }

  public DeliveryCompanyNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
