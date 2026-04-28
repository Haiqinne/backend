package com.bookstore.backend.controller;

import com.bookstore.backend.dto.*;
import com.bookstore.backend.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    // CREATE
    @PostMapping
    public PromotionDTO create(@RequestBody CreatePromotionRequest request) {
        return promotionService.create(request);
    }

    // UPDATE
    @PutMapping("/{id}")
    public PromotionDTO update(
            @PathVariable Long id,
            @RequestBody CreatePromotionRequest request
    ) {
        return promotionService.update(id, request);
    }

    // GET ALL
    @GetMapping
    public List<PromotionDTO> getAll() {
        return promotionService.getAll();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        promotionService.delete(id);
    }

    // APPLY PROMOTION TO PRODUCT
    @PostMapping("/apply")
    public String apply(@RequestBody ApplyPromotionRequest request) {
        promotionService.applyPromotion(request);
        return "Apply success";
    }
}