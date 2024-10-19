package com.yangnjo.dessert_atelier.domain_service.member.exception;

public class MemberAlreadyExistException extends RuntimeException {
  public MemberAlreadyExistException() {
    super();
  }

  public MemberAlreadyExistException(Throwable ex) {
    super(ex);
  }

  public MemberAlreadyExistException(String message) {
    super(message);
  }

  public MemberAlreadyExistException(String message, Throwable ex) {
    super(message, ex);
  }

}
