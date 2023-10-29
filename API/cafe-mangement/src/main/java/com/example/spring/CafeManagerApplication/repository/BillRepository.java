package com.example.spring.CafeManagerApplication.repository;

import com.example.spring.CafeManagerApplication.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill,Integer> {

}
