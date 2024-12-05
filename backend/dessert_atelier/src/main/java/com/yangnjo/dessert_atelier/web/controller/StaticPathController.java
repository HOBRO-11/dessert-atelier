package com.yangnjo.dessert_atelier.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class StaticPathController {

    @GetMapping("/auth/login")
    public String getMethodName() {
        return "forward:/html/login.html";
    }

    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        return "forward:/html/hello.html";
    }

    @GetMapping("/access-deny")
    public String accessDeny() {
        return "forward:/html/accessDeny.html";
    }
}
