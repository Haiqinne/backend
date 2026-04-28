package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.ProductDTO;
import com.bookstore.backend.dto.ProductRequest;
import com.bookstore.backend.entity.*;
import com.bookstore.backend.repository.*;
import com.bookstore.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;
    private final ImageRepository imageRepository;
    private final AuthorDetailRepository authorDetailRepository;

    // convert entity -> DTO
    private ProductDTO mapToDTO(Product p) {
        ProductDTO dto = new ProductDTO();

        dto.setProductId(p.getProductId());
        dto.setProductName(p.getProductName());
        dto.setPrice(p.getPrice());
        dto.setProductQuantity(p.getProductQuantity());
        dto.setDescription(p.getDescription());
        dto.setPageNumber(p.getPageNumber());
        dto.setPublicationYear(p.getPublicationYear());
        dto.setStock(p.getStock());

        // ✅ GENRE + CATEGORY
        if (p.getGenre() != null) {
            dto.setGenreId(p.getGenre().getGenreId());
            dto.setGenreName(p.getGenre().getGenreName());

            if (p.getGenre().getCategory() != null) {
                dto.setCategoryId(p.getGenre().getCategory().getCategoryId());
                dto.setCategoryName(p.getGenre().getCategory().getCategoryName());
            }
        }

        // ✅ PUBLISHER
        if (p.getPublisher() != null) {
            dto.setPublisherId(p.getPublisher().getPublisherId());
            dto.setPublisherName(p.getPublisher().getPublisherName());
        }

        // ✅ IMAGES
        List<String> images = imageRepository.findByProduct_ProductId(p.getProductId())
                .stream()
                .map(Image::getImageUrl)
                .toList();
        dto.setImages(images);

        // ✅ AUTHORS (QUAN TRỌNG)
        List<String> authors = authorDetailRepository.findByProduct_ProductId(p.getProductId())
                .stream()
                .map(ad -> ad.getAuthor().getAuthorName())
                .toList();
        dto.setAuthorNames(authors);

        return dto;
    }
    // 1. get all (list)
    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // 2. get all pageable
    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // 3. get by id
    @Override
    public ProductDTO getProductById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDTO(p);
    }

    // 4. get by genre
    @Override
    public Page<ProductDTO> getProductsByGenre(Long genreId, Pageable pageable) {
        return productRepository.findByGenre_GenreId(genreId, pageable)
                .map(this::mapToDTO);
    }

    // 5. search
    @Override
    public Page<ProductDTO> searchAdvanced(String keyword, Integer year, Pageable pageable) {
        return productRepository.searchAdvanced(keyword, year, pageable)
                .map(this::mapToDTO);
    }

    // 6. new arrivals
    @Override
    public List<ProductDTO> getNewArrivals() {
        return productRepository.findTop8ByOrderByProductIdDesc()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // 7. create
    @Override
    public ProductDTO createProduct(ProductRequest request) {
        Product p = new Product();

        p.setProductName(request.getProductName());
        p.setPrice(request.getPrice());
        p.setProductQuantity(request.getProductQuantity());
        p.setDescription(request.getDescription());
        p.setPageNumber(request.getPageNumber());
        p.setPublicationYear(request.getPublicationYear());
        p.setStock(request.getStock());

        Genre genre = genreRepository.findById(request.getGenreId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        p.setGenre(genre);

        Publisher publisher = publisherRepository.findById(request.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        p.setPublisher(publisher);

        return mapToDTO(productRepository.save(p));
    }

    // 8. update
    @Override
    public ProductDTO updateProduct(Long id, ProductRequest request) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        p.setProductName(request.getProductName());
        p.setPrice(request.getPrice());
        p.setProductQuantity(request.getProductQuantity());
        p.setDescription(request.getDescription());
        p.setPageNumber(request.getPageNumber());
        p.setPublicationYear(request.getPublicationYear());
        p.setStock(request.getStock());

        Genre genre = genreRepository.findById(request.getGenreId()).orElseThrow();
        p.setGenre(genre);

        Publisher publisher = publisherRepository.findById(request.getPublisherId()).orElseThrow();
        p.setPublisher(publisher);

        return mapToDTO(productRepository.save(p));
    }

    // 9. delete
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}