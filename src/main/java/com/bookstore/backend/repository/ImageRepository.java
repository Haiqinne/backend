package com.bookstore.backend.repository;

import com.bookstore.backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProduct_ProductId(Long productId);
}