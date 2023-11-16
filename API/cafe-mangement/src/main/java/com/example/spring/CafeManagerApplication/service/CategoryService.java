package com.example.spring.CafeManagerApplication.service;

import com.example.spring.CafeManagerApplication.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CategoryService {
    ResponseEntity<?> addNewCategory(Map<String, String> requestMap);

    ResponseEntity<?> getAllCategories();
    ResponseEntity<?> enableCate(Integer id);

    ResponseEntity<?> updateCategory(Map<String, String> requestMap);
}
