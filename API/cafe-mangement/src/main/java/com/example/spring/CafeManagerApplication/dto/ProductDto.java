package com.example.spring.CafeManagerApplication.dto;

import com.example.spring.CafeManagerApplication.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Data
public class ProductDto {

    private Integer productId;

    private Integer quantity;

    private String name;

    private String description;

    private Integer price;

    private String category;

}
