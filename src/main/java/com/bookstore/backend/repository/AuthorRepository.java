package com.bookstore.backend.repository;

import com.bookstore.backend.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // tìm theo tên (gần đúng)
    List<Author> findByAuthorNameContainingIgnoreCase(String name);

    // tìm chính xác
    Optional<Author> findByAuthorName(String name);

    // check tồn tại
    boolean existsByAuthorName(String name);
}