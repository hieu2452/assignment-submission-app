package com.example.spring.assaignmentsubmissionapp.controllers;


import com.example.spring.assaignmentsubmissionapp.dto.AuthResponseDTO;
import com.example.spring.assaignmentsubmissionapp.dto.LoginDto;
import com.example.spring.assaignmentsubmissionapp.dto.RegisterDto;
import com.example.spring.assaignmentsubmissionapp.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterDto registerDto){

        return new ResponseEntity<>(authenticationService.register(registerDto), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(authenticationService.login(loginDto));
    }
}
