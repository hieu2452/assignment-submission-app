package com.example.spring.CafeManagerApplication.service;

import com.example.spring.CafeManagerApplication.dto.ProductDto;
import com.example.spring.CafeManagerApplication.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
//    ResponseEntity<?> addNewProduct(ProductDto productDto);
    ResponseEntity<List<Product>> getAllProduct();
    ResponseEntity<?> addNewProduct(MultipartFile file,String model) throws IOException;

}
