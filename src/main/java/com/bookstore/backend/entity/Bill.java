package com.bookstore.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    @JsonIgnore
    private Address address;

    private LocalDateTime time;
    private String status;
    private Double totalMoney; // ✅ NEW

    private String paymentMethod; // ✅ NEW
    private String qrCode;        // ✅ NEW


    // ✅ FIX QUAN TRỌNG
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<BillDetail> billDetails = new ArrayList<>();
}
