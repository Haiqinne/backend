package com.bookstore.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDetailDTO {
    private Long productId;
    private String productName;
    private Double promotionPrice;
    private Double promotionPercent;
}