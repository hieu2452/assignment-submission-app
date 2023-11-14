package com.example.spring.CafeManagerApplication.controllers;

import com.example.spring.CafeManagerApplication.service.AdminService;
import com.example.spring.CafeManagerApplication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("update-status/{userId}")
    public ResponseEntity<?> enableUserStatus(@PathVariable Long userId) {
        return adminService.enableUser(userId);
    }

    @GetMapping("get-user")
    public ResponseEntity<?> getUser(){
        return adminService.getUser();
    }
}
