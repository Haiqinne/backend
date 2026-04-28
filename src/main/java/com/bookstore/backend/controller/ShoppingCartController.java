package com.bookstore.backend.controller;

import com.bookstore.backend.dto.*;
import com.bookstore.backend.security.JwtUtil;
import com.bookstore.backend.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService cartService;
    private final JwtUtil jwtUtil;

    // ================= GET CART =================
    @GetMapping
    public ShoppingCartDTO getCart(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        return cartService.getCart(userId);
    }

    // ================= ADD =================
    // ✅ Trả ShoppingCartDTO để FE cập nhật cart ngay sau khi thêm
    @PostMapping("/add")
    public ShoppingCartDTO add(@RequestBody AddToCartRequest request,
                               HttpServletRequest http) {
        Long userId = getUserIdFromRequest(http);
        return cartService.addToCart(userId, request.getProductId(), request.getQuantity());
    }

    // ================= UPDATE =================
    @PutMapping("/update")
    public ShoppingCartDTO update(@RequestBody UpdateCartRequest request,
                                  HttpServletRequest http) {
        Long userId = getUserIdFromRequest(http);
        return cartService.updateItem(userId, request.getProductId(), request.getQuantity());
    }

    // ================= REMOVE =================
    @DeleteMapping("/{productId}")
    public ShoppingCartDTO remove(@PathVariable Long productId,
                                  HttpServletRequest http) {
        Long userId = getUserIdFromRequest(http);
        return cartService.removeItem(userId, productId);
    }

    // ================= CLEAR =================
    @DeleteMapping("/clear")
    public void clear(HttpServletRequest http) {
        Long userId = getUserIdFromRequest(http);
        cartService.clearCart(userId);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCartCount(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        Integer count = cartService.getCartItemCount(userId);
        return ResponseEntity.ok(count);
    }


    // ================= Helper =================
    private Long getUserIdFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = header.substring(7);

        // ✅ Token được tạo bởi generateToken(User) nên có claim "id"
        Long userId = jwtUtil.extractUserId(token);

        if (userId == null) {
            throw new RuntimeException(
                    "Cannot extract userId from token. " +
                            "Make sure AuthServiceImpl calls generateToken(user) not generateToken(username)."
            );
        }

        return userId;
    }
}