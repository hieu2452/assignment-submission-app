package com.example.spring.CafeManagerApplication.repository;

import com.example.spring.CafeManagerApplication.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill,Integer> {

    List<Bill> findByUser_Id(Long userId);
}
