package com.example.spring.CafeManagerApplication.service;

import com.example.spring.CafeManagerApplication.entity.Category;
import org.springframework.http.ResponseEntity;

public interface DashboardService {
    ResponseEntity<?> getDetails();
}
