package com.example.spring.CafeManagerApplication.service.ServiceImpl;

import com.example.spring.CafeManagerApplication.entity.Category;
import com.example.spring.CafeManagerApplication.repository.CategoryRepository;
import com.example.spring.CafeManagerApplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    @Transactional
    public ResponseEntity<?> addNewCategory(Map<String, String> requestMap) {

        if(!validateCategoryMap(requestMap))
            return new ResponseEntity<>("You have submitted wrong format, pleas try again", HttpStatus.BAD_REQUEST);

        Category category = new Category();
        category.setName(requestMap.get("name"));
        categoryRepository.save(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name");
    }
}
