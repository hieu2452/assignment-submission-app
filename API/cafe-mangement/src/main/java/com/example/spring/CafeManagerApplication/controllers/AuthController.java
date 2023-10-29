package com.example.spring.CafeManagerApplication.controllers;


import com.example.spring.CafeManagerApplication.dto.LoginDto;
import com.example.spring.CafeManagerApplication.dto.RegisterDto;
import com.example.spring.CafeManagerApplication.service.ServiceImpl.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){

        return authenticationService.register(registerDto);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        return authenticationService.login(loginDto);
    }
}
