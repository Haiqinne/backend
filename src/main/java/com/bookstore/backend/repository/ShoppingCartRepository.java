package com.bookstore.backend.repository;

import com.bookstore.backend.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser_IdAndIsActiveTrue(Long userId);
}
