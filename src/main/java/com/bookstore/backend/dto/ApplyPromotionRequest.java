package com.bookstore.backend.dto;

public class ApplyPromotionRequest {

    private Long productId;
    private Long promotionId;
    private Double promotionPrice;
    private Double promotionPercent;

    public Long getProductId() {
        return productId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public Double getPromotionPercent() {
        return promotionPercent;
    }
}