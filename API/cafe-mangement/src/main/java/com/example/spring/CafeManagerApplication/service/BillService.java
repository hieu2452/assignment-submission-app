package com.example.spring.CafeManagerApplication.service;

import com.example.spring.CafeManagerApplication.dto.BillDetailDto;
import org.springframework.http.ResponseEntity;

public interface BillService {
    ResponseEntity<?> addNewBill(BillDetailDto billDetailDto);

    ResponseEntity<?> getAllBill();

    ResponseEntity<?> getBillById(Integer id);
}
