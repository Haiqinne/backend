package com.bookstore.backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PromotionDTO {
    private Long promotionId;
    private String promotionName;
    private LocalDate startDate;
    private LocalDate  endDate;
    private List<PromotionDetailDTO> products;
}