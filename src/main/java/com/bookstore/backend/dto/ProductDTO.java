package com.bookstore.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Long productId;
    private String productName;
    private Double price;
    private Integer productQuantity;
    private String description;
    private Integer pageNumber;
    private Integer publicationYear;
    private Integer stock;

    private Long genreId;
    private String genreName;

    private Long categoryId;
    private String categoryName;

    private Long publisherId;
    private String publisherName;

    private List<String> images;
    // ✅ ADD
    private List<String> authorNames;
}