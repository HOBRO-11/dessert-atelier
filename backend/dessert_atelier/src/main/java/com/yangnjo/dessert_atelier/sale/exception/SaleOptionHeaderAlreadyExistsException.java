package com.yangnjo.dessert_atelier.sale.exception;

public class SaleOptionHeaderAlreadyExistsException extends RuntimeException{

    public SaleOptionHeaderAlreadyExistsException(){
        super();
    }

    public SaleOptionHeaderAlreadyExistsException(String message){
        super(message);
    }
}
