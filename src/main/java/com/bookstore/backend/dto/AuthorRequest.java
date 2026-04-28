package com.bookstore.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequest {
    private String authorName;
}