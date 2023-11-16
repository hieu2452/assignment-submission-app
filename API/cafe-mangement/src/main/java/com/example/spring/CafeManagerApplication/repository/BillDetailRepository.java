package com.example.spring.CafeManagerApplication.repository;

import com.example.spring.CafeManagerApplication.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillDetailRepository extends JpaRepository<BillDetail,Integer> {
//    @Query("DELETE bd FROM BillDetail bd WHERE bd.bill.id = ?1")
//    @Modifying
//    int delete(Integer id);
}
