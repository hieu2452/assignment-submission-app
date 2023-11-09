package com.example.spring.CafeManagerApplication.controllers;

import com.example.spring.CafeManagerApplication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AuthenticationService authenticationService;
}
