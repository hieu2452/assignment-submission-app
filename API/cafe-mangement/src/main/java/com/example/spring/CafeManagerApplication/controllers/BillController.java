package com.example.spring.CafeManagerApplication.controllers;

import com.example.spring.CafeManagerApplication.dto.BillRequestDto;
import com.example.spring.CafeManagerApplication.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping("add")
    public ResponseEntity<?> addNewBill(@RequestBody BillRequestDto billRequestDto){
        return billService.addNewBill(billRequestDto);
    }
}
