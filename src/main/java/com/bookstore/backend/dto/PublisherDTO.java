package com.bookstore.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {
    private Long publisherId;
    private String publisherName;
}