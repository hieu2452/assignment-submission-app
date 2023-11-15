package com.example.spring.CafeManagerApplication.service;

import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<?> enableUser(Long userId);
    ResponseEntity<?> getUser();
}
