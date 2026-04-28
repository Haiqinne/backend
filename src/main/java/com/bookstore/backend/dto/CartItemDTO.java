package com.bookstore.backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private Double total;

    private String image; // ✅ thêm field ảnh
}