package com.example.spring.CafeManagerApplication.repository;
import com.example.spring.CafeManagerApplication.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
