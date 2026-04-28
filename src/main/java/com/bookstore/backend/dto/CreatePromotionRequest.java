package com.bookstore.backend.dto;

import java.time.LocalDate;

public class CreatePromotionRequest {

    private String promotionName;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getPromotionName() {
        return promotionName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}