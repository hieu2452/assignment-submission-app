package com.example.spring.CafeManagerApplication.controllers;

import com.example.spring.CafeManagerApplication.Utils.ProductFilters;
import com.example.spring.CafeManagerApplication.dto.ProductDto;
import com.example.spring.CafeManagerApplication.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

//    @PostMapping("add")
//    public ResponseEntity<?> addNewProduct(@RequestBody ProductDto productDto) {
//        return productService.addNewProduct(productDto);
//    }

    @PostMapping(value = "add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addNewProduct1(@RequestParam("file") MultipartFile file, @RequestParam("model") String model) throws IOException {


        return productService.addNewProduct(file,model);
    }

    @GetMapping("get")
    public ResponseEntity<?> getAllProduct(@ModelAttribute ProductFilters productFilters) {
        return productService.getAllProduct(productFilters);
    }

    @GetMapping("admin/get")
    public ResponseEntity<?> getAllProductAdmin(@ModelAttribute ProductFilters productFilters) {
        return productService.getAllProductAdmin(productFilters);
    }

    @GetMapping("getByCategory/{id}")
    public ResponseEntity<?> getByCategory(@PathVariable Integer id) {
        return productService.getByCategory(id);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) { return productService.getProductById(id); }

    @PostMapping(value = "update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam("model") String model) throws IOException {
        return productService.updateProduct(file,model);
    }

    @PutMapping("update-status/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id) throws IOException {
        return productService.updateProductStatus(id);
    }
}
