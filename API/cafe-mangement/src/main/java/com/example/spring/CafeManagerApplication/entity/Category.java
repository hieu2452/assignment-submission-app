package com.example.spring.CafeManagerApplication.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    private String enable = "true";

    public Category(Integer id, String name, String enable) {
        this.id = id;
        this.name = name;
        this.enable = enable;
    }
}
