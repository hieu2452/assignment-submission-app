package com.example.spring.CafeManagerApplication.repository;

import com.example.spring.CafeManagerApplication.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {

//    @Query("SELECT p FROM Product p join p.category c WHERE c.id = ?1")
//    List<Product> getProductsByCategory(Integer id);
    @Query("SELECT p FROM Product p WHERE p.category.id = ?1")
    List<Product> getProductsByCategory(Integer id);
    Boolean existsByName(String productName);
}
