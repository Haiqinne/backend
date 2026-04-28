package com.bookstore.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoryDTO {

    private Long categoryId;
    private String categoryName;

    private List<GenreDTO> genres;
}