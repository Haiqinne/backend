package com.bookstore.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "shopping_cart_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDetail {

    @EmbeddedId
    private ShoppingCartDetailId id;

    @ManyToOne
    @MapsId("shoppingCart")
    @JoinColumn(name = "shopping_cart_id")
    @JsonIgnore
    private ShoppingCart shoppingCart;

    @ManyToOne
    @MapsId("product")
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private Double total;

    private Double price;
}
