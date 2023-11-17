package com.example.spring.CafeManagerApplication.service.ServiceImpl;

import com.example.spring.CafeManagerApplication.repository.BillRepository;
import com.example.spring.CafeManagerApplication.repository.CategoryRepository;
import com.example.spring.CafeManagerApplication.repository.ProductRepository;
import com.example.spring.CafeManagerApplication.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public ResponseEntity<?> getDetails() {
        Map<String,Long> response = new HashMap<>();

        response.put("products",productRepository.count());
        response.put("categories", categoryRepository.count());
        response.put("bills",billRepository.count());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
