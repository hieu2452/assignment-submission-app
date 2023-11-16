package com.example.spring.CafeManagerApplication.service.ServiceImpl;

import com.example.spring.CafeManagerApplication.dto.MessageResponse;
import com.example.spring.CafeManagerApplication.entity.Category;
import com.example.spring.CafeManagerApplication.repository.CategoryRepository;
import com.example.spring.CafeManagerApplication.security.JWTAuthenticationFilter;
import com.example.spring.CafeManagerApplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
//    @Autowired
//    JWTAuthenticationFilter jwtFilter;
    @Override
    @Transactional
    public ResponseEntity<?> addNewCategory(Map<String, String> requestMap) {

        if(!validateCategoryMap(requestMap))
            return new ResponseEntity<>(new MessageResponse("You have submitted wrong format, pleas try again"), HttpStatus.BAD_REQUEST);

        if(categoryRepository.existsByName(requestMap.get("name")))
            return new ResponseEntity<>(new MessageResponse("Category already exists"), HttpStatus.BAD_REQUEST);

        Category category = new Category();
        category.setName(requestMap.get("name"));
        categoryRepository.save(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(categoryRepository.findAll(),HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<?> enableCate(Integer id) {
        Optional<Category> optional = categoryRepository.findById(id);

        if(optional.isEmpty()) return new ResponseEntity<>("", HttpStatus.NOT_FOUND);

        Category category = optional.get();

        if (category.getEnable().equals("true"))
            category.setEnable("false");
        else category.setEnable("true");

        return new ResponseEntity<>("",HttpStatus.NO_CONTENT);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name");
    }
}
