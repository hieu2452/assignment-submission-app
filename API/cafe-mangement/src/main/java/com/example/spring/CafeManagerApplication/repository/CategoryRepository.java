package com.example.spring.CafeManagerApplication.repository;
import com.example.spring.CafeManagerApplication.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category,Integer> {


    Category findByName(String name);

    @Query("UPDATE Category c SET c.enable = ?2 WHERE c.id = ?1")
    @Modifying
    void updateEnableStatus(Integer id, String status);


}
