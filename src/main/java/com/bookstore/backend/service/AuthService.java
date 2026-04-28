package com.bookstore.backend.service;

import com.bookstore.backend.dto.LoginRequest;
import com.bookstore.backend.dto.LoginResponse;
import com.bookstore.backend.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}