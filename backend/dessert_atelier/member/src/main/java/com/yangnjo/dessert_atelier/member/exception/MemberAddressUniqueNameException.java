package com.yangnjo.dessert_atelier.member.exception;

public class MemberAddressUniqueNameException extends RuntimeException {

    public MemberAddressUniqueNameException(String naming) {
        super(naming);
    }       

}
