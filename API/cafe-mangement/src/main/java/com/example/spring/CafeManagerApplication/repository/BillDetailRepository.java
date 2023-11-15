package com.example.spring.CafeManagerApplication.repository;

import com.example.spring.CafeManagerApplication.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillDetailRepository extends JpaRepository<BillDetail,Integer> {
    @Query(value = "select * from bill_detail b where b.bill_id = ?id", nativeQuery = true)
    List<BillDetail> findALlByByBIllId(Integer id);
}
