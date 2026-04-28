package com.bookstore.backend.service;

import com.bookstore.backend.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<ImageDTO> getByProduct(Long productId);

    ImageDTO upload(Long productId, MultipartFile file);

    void delete(Long id);
}