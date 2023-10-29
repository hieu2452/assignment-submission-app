package com.example.spring.CafeManagerApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "bill")
@NoArgsConstructor
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "contactnumber")
    private String contactNumber;

    @Column(name = "paymentmethod")
    private String paymentMethod;


    @Column(name = "createdDate")
    private LocalDate createdDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public Bill(Integer id, String name, String email, String contactNumber, String paymentMethod, LocalDate createdDate, UserEntity user) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.paymentMethod = paymentMethod;
        this.createdDate = createdDate;
        this.user = user;
    }
}
