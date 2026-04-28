package com.bookstore.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDetailId implements Serializable {

    private Long shoppingCart;
    private Long product;
}