package com.example.spring.CafeManagerApplication.repository;

import com.example.spring.CafeManagerApplication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Boolean existsByName(String productName);
}
