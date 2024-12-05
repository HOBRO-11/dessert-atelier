package com.yangnjo.dessert_atelier.provider;


import org.springframework.http.HttpHeaders;

import jakarta.servlet.http.HttpServletResponse;

public record TokenHeader(String name, String body) {

    public void setHeader(HttpHeaders headers){
        headers.add(name, body);
    }

    public void addCookie(HttpServletResponse response){
        response.addHeader(name, body);
    }
}
