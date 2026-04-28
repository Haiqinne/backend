package com.bookstore.backend.dto;

public class LoginResponse {

    private String token;
    private String role;
    private String username;
    private Long userId;   // ← thêm để frontend biết id mà gọi API

    public LoginResponse(String token, String role, String username, Long userId) {
        this.token    = token;
        this.role     = role;
        this.username = username;
        this.userId   = userId;
    }

    public String getToken()    { return token;    }
    public String getRole()     { return role;     }
    public String getUsername() { return username; }
    public Long   getUserId()   { return userId;   }
}