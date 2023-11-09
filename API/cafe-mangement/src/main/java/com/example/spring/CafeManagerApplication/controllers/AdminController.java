package com.example.spring.CafeManagerApplication.controllers;

import com.example.spring.CafeManagerApplication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("update-status/{userId}")
    public ResponseEntity<?> enableUserStatus(@PathVariable Long userId) {
        return authenticationService.enableUser(userId);
    }
}
