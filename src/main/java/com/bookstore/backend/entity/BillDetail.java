package com.bookstore.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bill_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDetail {

    @EmbeddedId
    private BillDetailId id;

    @ManyToOne
    @MapsId("bill")
    @JoinColumn(name = "bill_id")
    @JsonIgnore
    private Bill bill;

    @ManyToOne
    @MapsId("product")
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    private Double priceAtPurchase; // ✅ NEW (giá tại thời điểm mua)
}
