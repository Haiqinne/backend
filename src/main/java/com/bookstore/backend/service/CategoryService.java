package com.bookstore.backend.service;

import com.bookstore.backend.dto.CategoryDTO;
import com.bookstore.backend.dto.CategoryRequest;
import com.bookstore.backend.entity.Category;

import java.util.List;

public interface CategoryService {

    // GET
    List<CategoryDTO> getAllCategoriesWithGenres();

    // CREATE
    CategoryDTO create(CategoryRequest request);

    // UPDATE
    CategoryDTO update(Long id, CategoryRequest request);

    // DELETE
    void delete(Long id);
}
