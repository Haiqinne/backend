package com.bookstore.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "genre")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;

    @Column(nullable = false)
    private String genreName;

    // n Genre - 1 Category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // 1 Genre - n Product
    @OneToMany(mappedBy = "genre")
    @JsonIgnore
    private List<Product> products;
}