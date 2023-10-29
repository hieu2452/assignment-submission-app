package com.example.spring.CafeManagerApplication.controllers;

import com.example.spring.CafeManagerApplication.entity.Category;
import com.example.spring.CafeManagerApplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("add")
    public ResponseEntity<?> addNewCategory(@RequestBody Map<String, String> requestMap){
        return categoryService.addNewCategory(requestMap);
    }
}
