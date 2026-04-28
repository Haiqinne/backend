package com.bookstore.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long authorId;
    private String authorName;
}