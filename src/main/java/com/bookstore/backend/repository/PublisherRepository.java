package com.bookstore.backend.repository;

import com.bookstore.backend.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    // tìm gần đúng
    List<Publisher> findByPublisherNameContainingIgnoreCase(String name);

    // tìm chính xác
    Optional<Publisher> findByPublisherName(String name);

    // check tồn tại
    boolean existsByPublisherName(String name);
}