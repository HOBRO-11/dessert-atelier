package com.yangnjo.dessert_atelier.domain_service.admin.exception;

public class StoreAdminNotFoundException extends RuntimeException {

  public StoreAdminNotFoundException() {
    super();
  }

  public StoreAdminNotFoundException(Throwable ex) {
    super(ex);
  }

  public StoreAdminNotFoundException(String message) {
    super(message);
  }

  public StoreAdminNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
