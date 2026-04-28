package com.bookstore.backend.repository;

import com.bookstore.backend.entity.ShoppingCartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartDetailRepository extends JpaRepository<ShoppingCartDetail, Long> {

    List<ShoppingCartDetail> findByShoppingCart_ShoppingCartId(Long cartId);

    Optional<ShoppingCartDetail> findByShoppingCart_ShoppingCartIdAndProduct_ProductId(Long cartId, Long productId);
}