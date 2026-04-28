package com.bookstore.backend.service;

import com.bookstore.backend.dto.ProductDTO;
import com.bookstore.backend.dto.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    // 1. Lấy tất cả sản phẩm (không phân trang)
    List<ProductDTO> getAllProducts();

    // 2. Lấy tất cả sản phẩm (có phân trang)
    Page<ProductDTO> getAllProducts(Pageable pageable);

    // 3. Lấy chi tiết 1 sản phẩm
    ProductDTO getProductById(Long id);

    // 4. Lấy sản phẩm theo thể loại (GENRE)
    Page<ProductDTO> getProductsByGenre(Long genreId, Pageable pageable);

    // 5. Tìm kiếm sản phẩm
    Page<ProductDTO> searchAdvanced(String keyword, Integer year, Pageable pageable);

    // 6. Lấy sản phẩm mới nhất
    List<ProductDTO> getNewArrivals();

    // 7. Thêm sản phẩm
    ProductDTO createProduct(ProductRequest request);

    // 8. Cập nhật sản phẩm
    ProductDTO updateProduct(Long id, ProductRequest request);

    // 9. Xóa sản phẩm
    void deleteProduct(Long id);
}