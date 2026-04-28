package com.bookstore.backend.repository;

import com.bookstore.backend.entity.PromotionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PromotionDetailRepository extends JpaRepository<PromotionDetail, Long> {

    List<PromotionDetail> findByProduct_ProductId(Long productId);

    List<PromotionDetail> findByPromotion_PromotionId(Long promotionId);

    Optional<PromotionDetail> findByProduct_ProductIdAndPromotion_PromotionId(
            Long productId, Long promotionId
    );
    boolean existsByProduct_ProductIdAndPromotion_StartDateLessThanEqualAndPromotion_EndDateGreaterThanEqual(
            Long productId,
            java.time.LocalDate endDate,
            java.time.LocalDate startDate
    );
    @Query("""
    SELECT pd FROM PromotionDetail pd
    WHERE pd.product.productId = :productId
    AND pd.promotion.startDate <= CURRENT_DATE
    AND pd.promotion.endDate >= CURRENT_DATE
""")
    Optional<PromotionDetail> findActivePromotion(Long productId);
}