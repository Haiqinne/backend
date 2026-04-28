package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.ImageDTO;
import com.bookstore.backend.entity.Image;
import com.bookstore.backend.entity.Product;
import com.bookstore.backend.repository.ImageRepository;
import com.bookstore.backend.repository.ProductRepository;
import com.bookstore.backend.service.FileService;
import com.bookstore.backend.service.ImageService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final FileService fileService;

    public ImageServiceImpl(ImageRepository imageRepository,
                            ProductRepository productRepository,
                            FileService fileService) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.fileService = fileService;
    }

    @Override
    public List<ImageDTO> getByProduct(Long productId) {
        return imageRepository.findByProduct_ProductId(productId)
                .stream()
                .map(i -> {
                    ImageDTO dto = new ImageDTO();
                    dto.setImageId(i.getImageId());
                    dto.setImageUrl("http://localhost:8080" + i.getImageUrl());
                    dto.setDescription(i.getDescription());
                    return dto;
                }).toList();
    }

    @Override
    public ImageDTO upload(Long productId, MultipartFile file) {
        try {
            String url = fileService.saveImage(file);

            Product product = productRepository.findById(productId).orElseThrow();

            Image img = new Image();
            img.setImageUrl(url);
            img.setProduct(product);

            imageRepository.save(img);

            ImageDTO dto = new ImageDTO();
            dto.setImageId(img.getImageId());
            dto.setImageUrl("http://localhost:8080" + url);

            return dto;

        } catch (IOException e) {
            throw new RuntimeException("Upload failed");
        }
    }

    @Override
    public void delete(Long id) {
        imageRepository.deleteById(id);
    }
}