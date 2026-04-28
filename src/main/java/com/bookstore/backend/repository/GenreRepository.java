package com.bookstore.backend.repository;

import com.bookstore.backend.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findByCategory_CategoryId(Long categoryId);

    Optional<Genre> findByGenreName(String genreName);

    boolean existsByGenreName(String genreName);
}