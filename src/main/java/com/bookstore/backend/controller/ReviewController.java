package com.bookstore.backend.controller;

import com.bookstore.backend.dto.ReviewRequest;
import com.bookstore.backend.dto.ReviewDTO;
import com.bookstore.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /* ── GET ALL — dùng cho admin ManageReviews ── */
    @GetMapping
    public List<ReviewDTO> getAll() {
        return reviewService.getAllReviews();
    }

    /* ── GET BY PRODUCT — dùng cho ProductDetail ── */
    @GetMapping("/product/{productId}")
    public List<ReviewDTO> getByProduct(@PathVariable Long productId) {
        return reviewService.getByProduct(productId);
    }

    /* ── CREATE — cần đăng nhập ── */
    @PostMapping
    public ReviewDTO create(Authentication auth,
                            @RequestBody ReviewRequest request) {
        String username = auth.getName();
        return reviewService.createReview(username, request);
    }

    /* ── DELETE ── */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }
}