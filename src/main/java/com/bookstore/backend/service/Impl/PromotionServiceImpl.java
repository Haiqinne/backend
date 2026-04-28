package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.*;
import com.bookstore.backend.entity.*;
import com.bookstore.backend.repository.*;
import com.bookstore.backend.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepo;
    private final PromotionDetailRepository detailRepo;
    private final ProductRepository productRepo;

    // ===== CREATE =====
    @Override
    public PromotionDTO create(CreatePromotionRequest request) {

        Promotion p = new Promotion();
        p.setPromotionName(request.getPromotionName());
        p.setStartDate(request.getStartDate());
        p.setEndDate(request.getEndDate());

        promotionRepo.save(p);

        PromotionDTO dto = new PromotionDTO();
        dto.setPromotionId(p.getPromotionId());
        dto.setPromotionName(p.getPromotionName());
        dto.setStartDate(p.getStartDate());
        dto.setEndDate(p.getEndDate());

        return dto;
    }
    //   UPDATE
    @Override
    public PromotionDTO update(Long id, CreatePromotionRequest request) {

        Promotion p = promotionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        // 🚨 CHECK overlap cho tất cả product đang dùng promotion này
        List<PromotionDetail> details = detailRepo
                .findByPromotion_PromotionId(id);

        for (PromotionDetail d : details) {

            boolean isOverlap = detailRepo
                    .existsByProduct_ProductIdAndPromotion_StartDateLessThanEqualAndPromotion_EndDateGreaterThanEqual(
                            d.getProduct().getProductId(),
                            request.getEndDate(),
                            request.getStartDate()
                    );

            if (isOverlap) {
                throw new RuntimeException(
                        "Không thể update: sản phẩm " + d.getProduct().getProductName()
                                + " bị trùng thời gian khuyến mãi"
                );
            }
        }

        // ===== UPDATE =====
        p.setPromotionName(request.getPromotionName());
        p.setStartDate(request.getStartDate());
        p.setEndDate(request.getEndDate());

        promotionRepo.save(p);

        PromotionDTO dto = new PromotionDTO();
        dto.setPromotionId(p.getPromotionId());
        dto.setPromotionName(p.getPromotionName());
        dto.setStartDate(p.getStartDate());
        dto.setEndDate(p.getEndDate());

        return dto;
    }
    // ===== GET ALL =====
    @Override
    public List<PromotionDTO> getAll() {
        return promotionRepo.findAll().stream().map(p -> {

            List<PromotionDetailDTO> products = detailRepo
                    .findByPromotion_PromotionId(p.getPromotionId())
                    .stream()
                    .map(d -> {
                        PromotionDetailDTO dto = new PromotionDetailDTO();
                        dto.setProductId(d.getProduct().getProductId());
                        dto.setProductName(d.getProduct().getProductName());
                        dto.setPromotionPrice(d.getPromotionPrice());
                        dto.setPromotionPercent(d.getPromotionPercent());
                        return dto;
                    })
                    .collect(Collectors.toList());

            PromotionDTO dto = new PromotionDTO();
            dto.setPromotionId(p.getPromotionId());
            dto.setPromotionName(p.getPromotionName());
            dto.setStartDate(p.getStartDate());
            dto.setEndDate(p.getEndDate());
            dto.setProducts(products);

            return dto;

        }).collect(Collectors.toList());
    }

    // ===== DELETE =====
    @Override
    public void delete(Long id) {
        promotionRepo.deleteById(id);
    }

    // ===== APPLY PROMOTION (COMPOSITE KEY CHUẨN) =====
    @Override
    public void applyPromotion(ApplyPromotionRequest request) {

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Promotion promotion = promotionRepo.findById(request.getPromotionId())
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        // 🚨 CHECK TRÙNG THỜI GIAN
        boolean isOverlap = detailRepo
                .existsByProduct_ProductIdAndPromotion_StartDateLessThanEqualAndPromotion_EndDateGreaterThanEqual(
                        product.getProductId(),
                        promotion.getEndDate(),
                        promotion.getStartDate()
                );

        if (isOverlap) {
            throw new RuntimeException("Sản phẩm đã có khuyến mãi trong khoảng thời gian này");
        }

        // ===== TẠO MỚI =====
        PromotionDetail pd = new PromotionDetail();

        PromotionDetailId id = new PromotionDetailId();
        id.setPromotion(promotion.getPromotionId());
        id.setProduct(product.getProductId());

        pd.setId(id);
        pd.setProduct(product);
        pd.setPromotion(promotion);
        pd.setPromotionPrice(request.getPromotionPrice());
        pd.setPromotionPercent(request.getPromotionPercent());

        detailRepo.save(pd);
    }
}