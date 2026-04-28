package com.bookstore.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "promotion_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDetail {

    @EmbeddedId
    private PromotionDetailId id;

    @ManyToOne
    @MapsId("promotion")
    @JoinColumn(name = "promotion_id")
    @JsonIgnore
    private Promotion promotion;

    @ManyToOne
    @MapsId("product")
    @JoinColumn(name = "product_id")
    private Product product;

    private Double promotionPrice;
    private Double promotionPercent;
}