package com.yangnjo.dessert_atelier.sale.exception;

public class ShopPageNotFoundException extends RuntimeException {

    public ShopPageNotFoundException() {
        super();
    }

    public ShopPageNotFoundException(Throwable ex) {
        super(ex);
    }

    public ShopPageNotFoundException(String message) {
        super(message);
    }

    public ShopPageNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
