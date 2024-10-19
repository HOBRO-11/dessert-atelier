package com.yangnjo.dessert_atelier.domain_service.admin.exception;

public class StoreAdminMustGeOneException extends RuntimeException {
  public StoreAdminMustGeOneException() {
    super();
  }

  public StoreAdminMustGeOneException(Throwable ex) {
    super(ex);
  }

  public StoreAdminMustGeOneException(String message) {
    super(message);
  }

  public StoreAdminMustGeOneException(String message, Throwable ex) {
    super(message, ex);
  }
}
