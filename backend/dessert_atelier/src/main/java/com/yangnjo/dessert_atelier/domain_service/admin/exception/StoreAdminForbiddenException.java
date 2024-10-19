package com.yangnjo.dessert_atelier.domain_service.admin.exception;

public class StoreAdminForbiddenException extends RuntimeException {
  public StoreAdminForbiddenException() {
    super();
  }

  public StoreAdminForbiddenException(Throwable ex) {
    super(ex);
  }

  public StoreAdminForbiddenException(String message) {
    super(message);
  }

  public StoreAdminForbiddenException(String message, Throwable ex) {
    super(message, ex);
  }

}
