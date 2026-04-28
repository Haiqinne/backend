package com.bookstore.backend.config;

import com.bookstore.backend.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Kích hoạt cấu hình CORS và tắt CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())

                // 2. Cấu hình phân quyền
                .authorizeHttpRequests(auth -> auth
                        // Cho phép Login/Register
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/users/**").permitAll()
                        .requestMatchers("/api/addresses/**").permitAll()
                        .requestMatchers("/api/cart/**").permitAll()
                        .requestMatchers("/api/payment/**").permitAll()
                        .requestMatchers("/api/dashboard/**").permitAll()

                        // Cho phép xem sản phẩm, tìm kiếm, ảnh (Tất cả method GET)
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers("/api/products/new").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").permitAll()

                        // ===== GENRE (THÊM ĐOẠN NÀY) =====
                        .requestMatchers(HttpMethod.GET, "/api/genres/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/genres/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/genres/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/genres/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/authors/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/authors/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/authors/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/authors/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/publishers/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/publishers/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/publishers/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/publishers/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/images/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/images/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/reviews/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/bills/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/bills/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/bills/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/bills/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/promotions/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/promotions/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/promotions/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/promotions/**").permitAll()


                        // TẠM THỜI: Cho phép POST/PUT/DELETE để bạn test Postman không bị 403
                        // Sau này khi làm xong Admin, bạn đổi permitAll() thành authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**").permitAll()

                        .anyRequest().authenticated()
                )

                // 3. Stateless Session cho JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 4. Cấu hình CORS chi tiết để sửa lỗi IllegalArgumentException
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // SỬA LỖI TẠI ĐÂY: Dùng allowedOriginPatterns instead of allowedOrigins("*")
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        configuration.setAllowCredentials(true); // Giữ true để gửi JWT qua cookie/header nếu cần

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}