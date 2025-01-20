package com.yangnjo.dessert_atelier.member.exception;

public class MemberAddressNotFoundException extends RuntimeException {

    public MemberAddressNotFoundException() {
        super();
    }

    public MemberAddressNotFoundException(Throwable ex) {
        super(ex);
    }

    public MemberAddressNotFoundException(String message) {
        super(message);
    }

    public MemberAddressNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
