package com.yangnjo.dessert_atelier.member.exception;

import com.yangnjo.dessert_atelier.member.domain.entity.MemberOrigin;

public class MemberAlreadyExistException extends RuntimeException {

    public MemberAlreadyExistException(MemberOrigin origin) {
        super(origin.name());
    }

    public MemberAlreadyExistException() {
        super();
    }

}
