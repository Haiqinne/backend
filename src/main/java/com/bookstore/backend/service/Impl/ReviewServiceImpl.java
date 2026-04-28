package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.ReviewDTO;
import com.bookstore.backend.dto.ReviewRequest;
import com.bookstore.backend.entity.Review;
import com.bookstore.backend.repository.ReviewRepository;
import com.bookstore.backend.repository.ProductRepository;
import com.bookstore.backend.repository.UserRepository;
import com.bookstore.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository  reviewRepo;
    private final ProductRepository productRepo;
    private final UserRepository    userRepo;

    /* ── Lấy TẤT CẢ review, mới nhất trước ── */
    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewRepo.findAll()
                .stream()
                .sorted((a, b) -> {
                    if (a.getTime() == null) return 1;
                    if (b.getTime() == null) return -1;
                    return b.getTime().compareTo(a.getTime());
                })
                .map(this::mapToDTO)
                .toList();
    }

    /* ── Lấy theo sản phẩm ── */
    @Override
    public List<ReviewDTO> getByProduct(Long productId) {
        return reviewRepo.findByProduct_ProductId(productId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    /* ── Tạo review — nhận username từ Authentication ── */
    @Override
    public ReviewDTO createReview(String username, ReviewRequest request) {

        var product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        var user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setTime(LocalDateTime.now());

        return mapToDTO(reviewRepo.save(review));
    }

    /* ── Xóa ── */
    @Override
    public void delete(Long reviewId) {
        reviewRepo.deleteById(reviewId);
    }

    /* ── Map entity → DTO ── */
    private ReviewDTO mapToDTO(Review r) {
        ReviewDTO dto = new ReviewDTO();
        dto.setReviewId(r.getReviewId());
        dto.setComment(r.getComment());
        dto.setRating(r.getRating());
        dto.setTime(r.getTime());
        dto.setProductId(r.getProduct().getProductId());
        dto.setProductName(r.getProduct().getProductName()); // ← thêm
        dto.setUsername(r.getUser() != null ? r.getUser().getUsername() : "Ẩn danh");
        return dto;
    }
}