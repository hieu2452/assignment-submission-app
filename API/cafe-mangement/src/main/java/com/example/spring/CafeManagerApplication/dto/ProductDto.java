package com.example.spring.CafeManagerApplication.dto;

import com.example.spring.CafeManagerApplication.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    private Integer id;

    private Integer quantity;

    private String name;

    private String description;

    private Integer price;

    private Category category;

    public ProductDto(Integer quantity, String name, Integer price) {
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }
}
