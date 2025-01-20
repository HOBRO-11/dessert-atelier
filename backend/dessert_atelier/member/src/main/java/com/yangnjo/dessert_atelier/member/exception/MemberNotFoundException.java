package com.yangnjo.dessert_atelier.member.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super();
    }

    public MemberNotFoundException(Throwable ex) {
        super(ex);
    }

    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
