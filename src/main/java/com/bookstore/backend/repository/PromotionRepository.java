package com.bookstore.backend.repository;

import com.bookstore.backend.entity.Promotion;
import com.bookstore.backend.entity.PromotionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    List<Promotion> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate today1, LocalDate today2
    );
}