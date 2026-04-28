package com.bookstore.backend.controller;

import com.bookstore.backend.dto.GenreDTO;
import com.bookstore.backend.dto.GenreRequest;
import com.bookstore.backend.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@CrossOrigin("*")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<GenreDTO> getAll() {
        return genreService.getAllGenres();
    }

    @GetMapping("/category/{categoryId}")
    public List<GenreDTO> getByCategory(@PathVariable Long categoryId) {
        return genreService.getGenresByCategory(categoryId);
    }

    @PostMapping
    public GenreDTO create(@RequestBody GenreRequest request) {
        return genreService.create(request);
    }

    @PutMapping("/{id}")
    public GenreDTO update(@PathVariable Long id,
                           @RequestBody GenreRequest request) {
        return genreService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        genreService.delete(id);
    }
}