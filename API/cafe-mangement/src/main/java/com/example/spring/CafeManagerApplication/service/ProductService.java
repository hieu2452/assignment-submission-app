package com.example.spring.CafeManagerApplication.service;

import com.example.spring.CafeManagerApplication.Utils.ProductFilters;
import com.example.spring.CafeManagerApplication.dto.ProductDto;
import com.example.spring.CafeManagerApplication.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
//    ResponseEntity<?> addNewProduct(ProductDto productDto);
    ResponseEntity<List<Product>> getAllProduct(ProductFilters productFilters);
    ResponseEntity<List<Product>> getAllProductAdmin(ProductFilters productFilters);
    ResponseEntity<?> addNewProduct(MultipartFile file,String model) throws IOException;

    ResponseEntity<String> updateProductStatus(Integer id);

    ResponseEntity<String> updateProduct(MultipartFile file,String model) throws IOException;

    ResponseEntity<?> getProductById(Integer id);

    ResponseEntity<?> getByCategory(Integer id);

}
