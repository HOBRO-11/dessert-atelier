package com.yangnjo.dessert_atelier.domain_service.admin.exception;

public class StoreAdminUnAuthException extends RuntimeException {
  public StoreAdminUnAuthException() {
    super();
  }

  public StoreAdminUnAuthException(Throwable ex) {
    super(ex);
  }

  public StoreAdminUnAuthException(String message) {
    super(message);
  }

  public StoreAdminUnAuthException(String message, Throwable ex) {
    super(message, ex);
  }

}
