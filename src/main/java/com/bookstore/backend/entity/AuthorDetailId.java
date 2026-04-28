package com.bookstore.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDetailId implements Serializable {

    private Long author;
    private Long product;
}