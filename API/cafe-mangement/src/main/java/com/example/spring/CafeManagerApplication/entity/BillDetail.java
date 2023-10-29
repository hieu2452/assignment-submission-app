package com.example.spring.CafeManagerApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bill_detail")
@Getter
@Setter
@NoArgsConstructor
public class BillDetail {

    @EmbeddedId
    private BillDetailKey id;

    public BillDetail(BillDetailKey id, Bill bill, Product product, Integer quantity) {
        this.id = id;
        this.bill = bill;
        this.product = product;
        this.quantity = quantity;
    }

    @ManyToOne
    @MapsId("billId")
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(name = "quantity")
    private Integer quantity;


}
