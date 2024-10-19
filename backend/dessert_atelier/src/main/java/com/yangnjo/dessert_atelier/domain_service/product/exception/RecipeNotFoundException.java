package com.yangnjo.dessert_atelier.domain_service.product.exception;

public class RecipeNotFoundException extends RuntimeException {
  public RecipeNotFoundException() {
    super();
  }

  public RecipeNotFoundException(Throwable ex) {
    super(ex);
  }

  public RecipeNotFoundException(String message) {
    super(message);
  }

  public RecipeNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
