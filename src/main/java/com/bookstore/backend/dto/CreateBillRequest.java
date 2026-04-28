package com.bookstore.backend.dto;

import lombok.*;
import java.util.List;

@Data
public class CreateBillRequest {

    private Long userId;
    private Long addressId;
    private String paymentMethod; // ✅ NEW
    private List<Item> items;

    @Data
    public static class Item {
        private Long productId;
        private Integer quantity;
    }
}