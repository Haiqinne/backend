package com.bookstore.backend.dto;

import lombok.Data;

@Data
public class GenreRequest {
    private String genreName;
    private Long categoryId;
}