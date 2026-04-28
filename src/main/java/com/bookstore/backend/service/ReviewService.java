package com.bookstore.backend.service;

import com.bookstore.backend.dto.ReviewDTO;
import com.bookstore.backend.dto.ReviewRequest;

import java.util.List;

public interface ReviewService {

    // ← thêm để admin lấy tất cả
    List<ReviewDTO> getAllReviews();

    // dùng trong ProductDetail
    List<ReviewDTO> getByProduct(Long productId);

    // dùng trong ReviewController (authentication lấy username)
    ReviewDTO createReview(String username, ReviewRequest request);

    // xóa
    void delete(Long reviewId);
}