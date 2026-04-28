package com.bookstore.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "author_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDetail {

    @EmbeddedId
    private AuthorDetailId id;

    @ManyToOne
    @MapsId("author")
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @MapsId("product")
    @JoinColumn(name = "product_id")
    private Product product;
}