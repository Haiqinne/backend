package com.bookstore.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Serve ảnh từ thư mục vật lý trên ổ cứng
        // Vite proxy: /images/1.jpg → localhost:8080/images/1.jpg → file trên disk
        registry.addResourceHandler("/images/**")
                .addResourceLocations(
                        // Thư mục trong project (src/main/resources/static/images/)
                        "classpath:/static/images/",
                        // Thư mục ngoài project (linh hoạt hơn, dùng khi FileService lưu ra ngoài)
                        "file:images/",
                        "file:F:/bookstore_uploads/images/"
                );

        // Serve upload files nếu FileService lưu vào /uploads
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(
                        "classpath:/static/uploads/",
                        "file:uploads/"
                );
    }
}