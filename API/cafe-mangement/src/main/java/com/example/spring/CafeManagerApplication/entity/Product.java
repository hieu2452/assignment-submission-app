package com.example.spring.CafeManagerApplication.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    public Product(Integer id, String name, Category category, String description, Integer price, Boolean status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    @Column(name = "name")
    private String name;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private Boolean status = true;

    @Column(name = "imageUrl")
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy="product", cascade= CascadeType.ALL, orphanRemoval=true)
    List<BillDetail> billDetails = new ArrayList<>();

}
