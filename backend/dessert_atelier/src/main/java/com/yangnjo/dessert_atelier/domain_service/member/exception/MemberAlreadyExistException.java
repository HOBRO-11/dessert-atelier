package com.yangnjo.dessert_atelier.domain_service.member.exception;

import com.yangnjo.dessert_atelier.domain_model.member.MemberOrigin;

import lombok.Getter;

@Getter
public class MemberAlreadyExistException extends RuntimeException {
    private MemberOrigin origin;
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

  /**
   * 이메일은 일치하나 MemberOrigin이 일치하지 않을 경우 사용
   */
  public MemberAlreadyExistException(MemberOrigin origin) {
      super();
      this.origin = origin;
  }

}
