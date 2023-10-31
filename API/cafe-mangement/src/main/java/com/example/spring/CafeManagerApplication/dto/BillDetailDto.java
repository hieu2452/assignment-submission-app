package com.example.spring.CafeManagerApplication.dto;

import lombok.Data;

import java.util.List;

@Data
public class BillDetailDto {

    private String name;

    private String email;

    private String contactNumber;

    private String paymentMethod;

    private List<ProductDto> products;

}