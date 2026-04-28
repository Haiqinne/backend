package com.bookstore.backend.dto;

import lombok.Data;

@Data
public class ImageDTO {
    private Long imageId;
    private String imageUrl;
    private String description;
}