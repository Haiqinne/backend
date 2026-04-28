package com.bookstore.backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailDTO {

    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price; // ✅ NEW
}