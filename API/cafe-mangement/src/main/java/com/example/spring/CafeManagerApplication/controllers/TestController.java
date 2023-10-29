package com.example.spring.CafeManagerApplication.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final AuthenticationManager authenticationManager;

    public TestController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;}

    @GetMapping("teststring")
    public String getString(@AuthenticationPrincipal UserDetails userDetails){


        return "User Details: " + userDetails.getUsername();
    }

    @GetMapping("teststring1")
    public String getString1(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return "User Details: " + userName;
    }

}
