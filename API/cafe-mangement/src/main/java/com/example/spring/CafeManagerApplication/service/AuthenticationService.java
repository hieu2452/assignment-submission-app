package com.example.spring.CafeManagerApplication.service;

import com.example.spring.CafeManagerApplication.dto.LoginDto;
import com.example.spring.CafeManagerApplication.dto.RefreshTokenRequest;
import com.example.spring.CafeManagerApplication.dto.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> register(RegisterDto registerDto);
    ResponseEntity<?> login(LoginDto LoginDto);


    ResponseEntity<?> refreshtoken(RefreshTokenRequest request);

}
