package com.bookstore.backend.service;

import com.bookstore.backend.dto.ShoppingCartDTO;

public interface ShoppingCartService {

    ShoppingCartDTO getCart(Long userId);

    // ✅ Trả ShoppingCartDTO sau mỗi thay đổi để controller có thể return ngay
    ShoppingCartDTO addToCart(Long userId, Long productId, Integer quantity);

    ShoppingCartDTO updateItem(Long userId, Long productId, Integer quantity);

    ShoppingCartDTO removeItem(Long userId, Long productId);

    Integer getCartItemCount(Long userId);

    void clearCart(Long userId);
}