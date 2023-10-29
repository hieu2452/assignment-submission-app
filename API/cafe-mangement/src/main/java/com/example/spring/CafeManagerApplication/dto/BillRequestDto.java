package com.example.spring.CafeManagerApplication.dto;

import com.example.spring.CafeManagerApplication.entity.BillDetail;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BillRequestDto {

    private String name;

    private String email;

    private String contactNumber;

    private String paymentMethod;

    private List<ProductIdDto> products;

}