package com.example.spring.CafeManagerApplication.controllers;

import com.example.spring.CafeManagerApplication.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class Dashboard {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("get-details")
    public ResponseEntity<?> getDetails(){
        return dashboardService.getDetails();
    }
}
