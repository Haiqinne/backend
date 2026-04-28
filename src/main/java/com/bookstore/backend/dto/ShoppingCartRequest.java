package com.bookstore.backend.dto;

import lombok.*;

@Data
public class ShoppingCartRequest {

    private Long userId;
    private String description;
}