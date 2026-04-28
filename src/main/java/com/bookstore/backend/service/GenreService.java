package com.bookstore.backend.service;

import com.bookstore.backend.dto.GenreDTO;
import com.bookstore.backend.dto.GenreRequest;

import java.util.List;

public interface GenreService {

    List<GenreDTO> getAllGenres();
    List<GenreDTO> getGenresByCategory(Long categoryId);

    GenreDTO create(GenreRequest request);
    GenreDTO update(Long id, GenreRequest request);
    void delete(Long id);
}