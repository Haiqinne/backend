package com.bookstore.backend.controller;

import com.bookstore.backend.dto.ImageDTO;
import com.bookstore.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    // 1. Lấy tất cả ảnh theo product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ImageDTO>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(imageService.getByProduct(productId));
    }

    // 2. Upload ảnh
    @PostMapping("/upload/{productId}")
    public ResponseEntity<ImageDTO> upload(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(imageService.upload(productId, file));
    }

    // 3. Xoá ảnh
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        imageService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}