package com.bookstore.backend.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private Double price;
    private Integer productQuantity;
    private String description;
    private Integer pageNumber;
    private Integer publicationYear;
    private Integer stock;

    private Long genreId;
    private Long publisherId;
}