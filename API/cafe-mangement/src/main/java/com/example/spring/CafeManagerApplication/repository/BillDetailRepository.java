package com.example.spring.CafeManagerApplication.repository;

import com.example.spring.CafeManagerApplication.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailRepository extends JpaRepository<BillDetail,Integer> {
}
