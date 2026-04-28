package com.bookstore.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data // 🔥 cái này sẽ tự sinh getter/setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDetailId implements Serializable {

    private Long promotion;
    private Long product;
}