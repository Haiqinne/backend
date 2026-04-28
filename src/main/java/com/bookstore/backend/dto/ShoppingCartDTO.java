package com.bookstore.backend.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {

    private Long shoppingCartId;
    private Long userId;
    private String description;

    // danh sách sản phẩm trong giỏ
    private List<CartItemDTO> items;
    private Double totalMoney; // ✅ thêm
}