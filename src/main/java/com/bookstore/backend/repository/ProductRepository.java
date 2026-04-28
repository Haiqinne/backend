package com.bookstore.backend.repository;

import com.bookstore.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. Search (không phân biệt hoa thường)
    Page<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageable);

    // 2. Lọc theo Genre (có phân trang)
    Page<Product> findByGenre_GenreId(Long genreId, Pageable pageable);

    // 3. Sản phẩm mới nhất
    List<Product> findTop8ByOrderByProductIdDesc();

    // 4. Lọc theo Genre (không phân trang)
    List<Product> findByGenre_GenreId(Long genreId);

    @Query("""
    SELECT p FROM Product p
    WHERE (:keyword IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')))
    AND (:year IS NULL OR p.publicationYear = :year)
""")
    Page<Product> searchAdvanced(String keyword, Integer year, Pageable pageable);
}