package com.example.spring.CafeManagerApplication.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class BillDetailDto {
    private Integer id;

    private String name;

    private LocalDateTime createdDate;

    private String email;

    private String contactNumber;

    private String paymentMethod;

    private List<ProductDto> products;

}