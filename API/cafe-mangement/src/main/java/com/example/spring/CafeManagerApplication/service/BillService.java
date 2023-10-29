package com.example.spring.CafeManagerApplication.service;

import com.example.spring.CafeManagerApplication.dto.BillRequestDto;
import org.springframework.http.ResponseEntity;

public interface BillService {
    ResponseEntity<?> addNewBill(BillRequestDto billRequestDto);
}
