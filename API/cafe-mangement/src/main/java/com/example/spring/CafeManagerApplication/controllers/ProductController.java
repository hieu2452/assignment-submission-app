package com.example.spring.CafeManagerApplication.controllers;

import com.example.spring.CafeManagerApplication.dto.ProductDto;
import com.example.spring.CafeManagerApplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("add")
    public ResponseEntity<?> addNewProduct(@RequestBody ProductDto productDto) {
        return productService.addNewProduct(productDto);
    }

    @GetMapping("get")
    public ResponseEntity<?> getAllProduct() {
        return productService.getAllProduct();
    }
}
