package com.yangnjo.dessert_atelier.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebController {

    @Secured("ROLE_member")
    @GetMapping("/api/auth")
    public String authPage() {
        return "forward:/html/auth.html";
    }

    @GetMapping("/")
    public String hello() {
        return "forward:/html/hello.html";
    }

    @GetMapping("/auth/login")
    public String getMethodName() {
        return "forward:/html/login.html";
    }

    @GetMapping("/access-deny")
    public String accessDeny() {
        return "forward:/html/accessDeny.html";
    }
}
