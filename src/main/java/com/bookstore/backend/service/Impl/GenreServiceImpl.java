package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.GenreDTO;
import com.bookstore.backend.dto.GenreRequest;
import com.bookstore.backend.entity.Category;
import com.bookstore.backend.entity.Genre;
import com.bookstore.backend.repository.CategoryRepository;
import com.bookstore.backend.repository.GenreRepository;
import com.bookstore.backend.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<GenreDTO> getAllGenres() {
        return genreRepository.findAll().stream().map(this::map).toList();
    }

    @Override
    public List<GenreDTO> getGenresByCategory(Long categoryId) {
        return genreRepository.findByCategory_CategoryId(categoryId)
                .stream().map(this::map).toList();
    }

    @Override
    public GenreDTO create(GenreRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow();

        Genre genre = new Genre();
        genre.setGenreName(request.getGenreName());
        genre.setCategory(category);

        return map(genreRepository.save(genre));
    }

    @Override
    public GenreDTO update(Long id, GenreRequest request) {

        return genreRepository.findById(id).map(g -> {

            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow();

            g.setGenreName(request.getGenreName());
            g.setCategory(category);

            return map(genreRepository.save(g));
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

    private GenreDTO map(Genre g) {
        GenreDTO dto = new GenreDTO();
        dto.setGenreId(g.getGenreId());
        dto.setGenreName(g.getGenreName());
        return dto;
    }
}