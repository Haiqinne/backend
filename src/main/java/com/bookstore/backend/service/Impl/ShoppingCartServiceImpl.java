package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.*;
import com.bookstore.backend.entity.*;
import com.bookstore.backend.repository.*;
import com.bookstore.backend.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository cartRepo;
    private final ShoppingCartDetailRepository detailRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final PromotionRepository promotionRepo; // ✅ thêm
    private String getFirstImage(Product product) {
        if (product.getImages() == null || product.getImages().isEmpty()) {
            return "/images/default.jpg";
        }

        var img = product.getImages().get(0);

        if (img.getImageUrl() != null) return img.getImageUrl();

        return "/images/default.jpg";
    }

    // ================= GET FINAL PRICE =================
    private double getFinalPrice(Product product) {

        List<Promotion> promotions = promotionRepo.findAll();

        LocalDate now = LocalDate.now();

        for (Promotion promo : promotions) {

            boolean isActive =
                    (promo.getStartDate() == null || !now.isBefore(promo.getStartDate())) &&
                            (promo.getEndDate()   == null || !now.isAfter(promo.getEndDate()));

            if (!isActive) continue;

            for (PromotionDetail d : promo.getPromotionDetails()) { // ✅ đúng field

                if (d.getProduct().getProductId().equals(product.getProductId())) {

                    if (d.getPromotionPrice() != null) {
                        return d.getPromotionPrice();
                    }

                    if (d.getPromotionPercent() != null) {
                        return Math.round(
                                product.getPrice() * (1 - d.getPromotionPercent() / 100.0)
                        );
                    }
                }
            }
        }

        return product.getPrice(); // fallback
    }

    // ================= GET OR CREATE =================
    private ShoppingCart getCartEntity(Long userId) {
        return cartRepo.findByUser_IdAndIsActiveTrue(userId)
                .orElseGet(() -> {
                    User user = userRepo.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    ShoppingCart cart = new ShoppingCart();
                    cart.setUser(user);
                    cart.setDescription("Cart of user " + userId);
                    cart.setIsActive(true);

                    return cartRepo.save(cart);
                });
    }

    // ================= GET CART =================
    @Override
    public ShoppingCartDTO getCart(Long userId) {
        return mapToDTO(getCartEntity(userId));
    }

    // ================= CART COUNT =================
    @Override
    public Integer getCartItemCount(Long userId) {

        ShoppingCart cart = cartRepo
                .findByUser_IdAndIsActiveTrue(userId)
                .orElse(null);

        if (cart == null) return 0;

        List<ShoppingCartDetail> items =
                detailRepo.findByShoppingCart_ShoppingCartId(cart.getShoppingCartId());

        if (items == null || items.isEmpty()) return 0;

        return items.stream()
                .mapToInt(ShoppingCartDetail::getQuantity)
                .sum();
    }

    // ================= ADD =================
    @Override
    public ShoppingCartDTO addToCart(Long userId, Long productId, Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        ShoppingCart cart = getCartEntity(userId);

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        ShoppingCartDetail detail = detailRepo
                .findByShoppingCart_ShoppingCartIdAndProduct_ProductId(
                        cart.getShoppingCartId(), productId)
                .orElse(null);

        int currentQty = (detail == null) ? 0 : detail.getQuantity();
        int newQty = currentQty + quantity;

        if (newQty > product.getStock()) {
            throw new RuntimeException(
                    "Vượt quá tồn kho. Còn " + product.getStock()
            );
        }

        double finalPrice = getFinalPrice(product); // ✅ giá giảm

        if (detail == null) {
            ShoppingCartDetailId id = new ShoppingCartDetailId();
            id.setShoppingCart(cart.getShoppingCartId());
            id.setProduct(productId);

            detail = new ShoppingCartDetail();
            detail.setId(id);
            detail.setShoppingCart(cart);
            detail.setProduct(product);
            detail.setQuantity(quantity);
            detail.setPrice(finalPrice); // ✅ lưu giá
        } else {
            detail.setQuantity(newQty);
            detail.setPrice(finalPrice); // update lại giá nếu cần
        }

        detail.setTotal(finalPrice * detail.getQuantity());

        detailRepo.save(detail);

        return mapToDTO(cart);
    }

    // ================= UPDATE =================
    @Override
    public ShoppingCartDTO updateItem(Long userId, Long productId, Integer quantity) {

        ShoppingCart cart = getCartEntity(userId);

        ShoppingCartDetail item = detailRepo
                .findByShoppingCart_ShoppingCartIdAndProduct_ProductId(
                        cart.getShoppingCartId(), productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không có trong giỏ"));

        if (quantity <= 0) {
            detailRepo.delete(item);
            return mapToDTO(cart);
        }

        if (quantity > item.getProduct().getStock()) {
            throw new RuntimeException("Vượt quá tồn kho");
        }

        double finalPrice = getFinalPrice(item.getProduct());

        item.setQuantity(quantity);
        item.setPrice(finalPrice);
        item.setTotal(finalPrice * quantity);

        detailRepo.save(item);

        return mapToDTO(cart);
    }

    // ================= REMOVE =================
    @Override
    public ShoppingCartDTO removeItem(Long userId, Long productId) {

        ShoppingCart cart = getCartEntity(userId);

        ShoppingCartDetail item = detailRepo
                .findByShoppingCart_ShoppingCartIdAndProduct_ProductId(
                        cart.getShoppingCartId(), productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không có trong giỏ"));

        detailRepo.delete(item);

        return mapToDTO(cart);
    }

    // ================= CLEAR =================
    @Override
    public void clearCart(Long userId) {

        ShoppingCart cart = getCartEntity(userId);

        List<ShoppingCartDetail> items =
                detailRepo.findByShoppingCart_ShoppingCartId(cart.getShoppingCartId());

        detailRepo.deleteAll(items);
    }

    // ================= MAP =================
    private ShoppingCartDTO mapToDTO(ShoppingCart cart) {

        List<CartItemDTO> items = detailRepo
                .findByShoppingCart_ShoppingCartId(cart.getShoppingCartId())
                .stream()
                .map(i -> new CartItemDTO(
                        i.getProduct().getProductId(),
                        i.getProduct().getProductName(),
                        i.getPrice(), // ✅ giá giảm
                        i.getQuantity(),
                        i.getTotal(),
                        getFirstImage(i.getProduct()) // ✅ thêm
                ))
                .toList();

        double totalMoney = items.stream()
                .mapToDouble(CartItemDTO::getTotal)
                .sum();

        return new ShoppingCartDTO(
                cart.getShoppingCartId(),
                cart.getUser().getId(),
                cart.getDescription(),
                items,
                totalMoney
        );
    }
}