package com.bookstore.backend.security;

import com.bookstore.backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "bookstore-secret-key-bookstore-secret-key";
    private final SecretKey key  = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ── Tạo token với đầy đủ claims ──────────────────────────
    // Overload 1: nhận User entity (dùng trong AuthController)
    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                // Thêm id, email, role vào claims để frontend decode được
                .claim("id",    user.getId())
                .claim("email", user.getEmail())
                .claim("role",  user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }

    // Overload 2: giữ lại chữ ký cũ (username only) để không break code cũ
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }

    // ── Extract claims ────────────────────────────────────────
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        try {
            Object id = extractClaims(token).get("id");
            return id == null ? null : ((Number) id).longValue();
        } catch (Exception e) {
            return null;
        }
    }

    public String extractEmail(String token) {
        return (String) extractClaims(token).get("email");
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username);
    }
}