package com.bookstore.backend.service;

import com.bookstore.backend.dto.*;

import java.util.List;

public interface PromotionService {

    PromotionDTO create(CreatePromotionRequest request);

    PromotionDTO update(Long id, CreatePromotionRequest request);

    List<PromotionDTO> getAll();

    void delete(Long id);

    void applyPromotion(ApplyPromotionRequest request);
}