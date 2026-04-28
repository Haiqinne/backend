package com.bookstore.backend.dto;

import lombok.Data;

@Data
public class UpdateCartRequest {

    private Long productId;
    private Integer quantity;
}
