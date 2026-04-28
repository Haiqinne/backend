package com.bookstore.backend.dto;

import lombok.*;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private String role;
}