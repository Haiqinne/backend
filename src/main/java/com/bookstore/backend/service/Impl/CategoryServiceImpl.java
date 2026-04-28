package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.CategoryDTO;
import com.bookstore.backend.dto.CategoryRequest;
import com.bookstore.backend.dto.GenreDTO;
import com.bookstore.backend.entity.Category;
import com.bookstore.backend.repository.CategoryRepository;
import com.bookstore.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategoriesWithGenres() {

        return categoryRepository.findAll().stream().map(category -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setCategoryId(category.getCategoryId());
            dto.setCategoryName(category.getCategoryName());

            List<GenreDTO> genres = category.getGenres().stream().map(g -> {
                GenreDTO gd = new GenreDTO();
                gd.setGenreId(g.getGenreId());
                gd.setGenreName(g.getGenreName());
                return gd;
            }).toList();

            dto.setGenres(genres);
            return dto;
        }).toList();
    }

    @Override
    public CategoryDTO create(CategoryRequest request) {

        Category category = new Category();
        category.setCategoryName(request.getCategoryName());

        Category saved = categoryRepository.save(category);

        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(saved.getCategoryId());
        dto.setCategoryName(saved.getCategoryName());

        return dto;
    }

    @Override
    public CategoryDTO update(Long id, CategoryRequest request) {

        return categoryRepository.findById(id).map(c -> {
            c.setCategoryName(request.getCategoryName());

            Category updated = categoryRepository.save(c);

            CategoryDTO dto = new CategoryDTO();
            dto.setCategoryId(updated.getCategoryId());
            dto.setCategoryName(updated.getCategoryName());

            return dto;
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}