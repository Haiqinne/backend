package com.bookstore.backend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long          reviewId;
    private String        comment;
    private int           rating;
    private LocalDateTime time;

    private Long          productId;
    private String        productName;  // ← thêm để ManageReviews hiển thị tên SP
    private String        username;
}