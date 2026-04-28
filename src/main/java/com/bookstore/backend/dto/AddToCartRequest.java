package com.bookstore.backend.dto;

import lombok.*;

@Data
public class AddToCartRequest {


    private Long productId;
    private Integer quantity;
}