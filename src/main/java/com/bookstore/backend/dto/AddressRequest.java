package com.bookstore.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private Long userId;

    private String city;
    private String street;
    private String detailAddress;

    private String name;
    private String phoneNumber;
}