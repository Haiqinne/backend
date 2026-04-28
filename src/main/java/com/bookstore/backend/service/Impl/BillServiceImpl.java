package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.*;
import com.bookstore.backend.entity.*;
import com.bookstore.backend.repository.*;
import com.bookstore.backend.service.BillService;
import com.bookstore.backend.service.SePayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepo;
    private final BillDetailRepository billDetailRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final AddressRepository addressRepo;
    private final PromotionDetailRepository promotionDetailRepo;
    private final SePayService sePayService;

    // ===== CREATE BILL =====
    @Override
    @Transactional
    public BillDTO createBill(CreateBillRequest request) {

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepo.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Bill bill = new Bill();
        bill.setUser(user);
        bill.setAddress(address);
        bill.setTime(LocalDateTime.now());
        bill.setStatus("PENDING");
        bill.setPaymentMethod(request.getPaymentMethod());

        double total = 0;
        List<BillDetail> details = new ArrayList<>();

        for (CreateBillRequest.Item item : request.getItems()) {

            Product product = productRepo.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Kiểm tra tồn kho
            if (item.getQuantity() > product.getStock()) {
                throw new RuntimeException(
                        "Sản phẩm " + product.getProductName() + " chỉ còn " + product.getStock()
                );
            }

            double price = product.getPrice();

            // Tính toán khuyến mãi (nếu có)
            Optional<PromotionDetail> promoOpt =
                    promotionDetailRepo.findActivePromotion(product.getProductId());

            if (promoOpt.isPresent()) {
                PromotionDetail promo = promoOpt.get();
                if (promo.getPromotionPrice() != null) {
                    price = promo.getPromotionPrice();
                } else if (promo.getPromotionPercent() != null) {
                    price = price * (1 - promo.getPromotionPercent() / 100.0);
                }
            }

            BillDetail detail = new BillDetail();

            // Cấu trúc ID phức hợp cho BillDetail
            BillDetailId id = new BillDetailId();
            id.setBill(bill.getBillId());
            id.setProduct(product.getProductId());

            detail.setId(id);
            detail.setBill(bill);
            detail.setProduct(product);
            detail.setQuantity(item.getQuantity());
            detail.setPriceAtPurchase(price);

            total += price * item.getQuantity();
            details.add(detail);

            // Cập nhật số lượng tồn kho (Tùy chọn, nếu bạn muốn trừ kho ngay khi đặt)
            // product.setStock(product.getStock() - item.getQuantity());
            // productRepo.save(product);
        }

        bill.setBillDetails(details);
        bill.setTotalMoney(total);

        // Lưu Bill để lấy BillId
        bill = billRepo.save(bill);

        // Xử lý thanh toán QR nếu phương thức là QR
        if ("QR".equals(request.getPaymentMethod())) {
            String qr = sePayService.generateQR(bill.getBillId(), bill.getTotalMoney());
            bill.setQrCode(qr);
            bill = billRepo.save(bill); // Cập nhật lại với mã QR
        }

        return mapToDTO(bill);
    }

    // ===== UPDATE STATUS =====
    @Override
    @Transactional
    public BillDTO updateStatus(Long billId, String status) {
        Bill bill = billRepo.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.setStatus(status);
        return mapToDTO(billRepo.save(bill));
    }

    // ===== GET BY USER =====
    @Override
    public List<BillDTO> getBillsByUser(Long userId) {
        return billRepo.findByUser_Id(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ===== GET ALL =====
    @Override
    public List<BillDTO> getAllBills() {
        return billRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ===== GET BILL BY ID =====
    @Override
    public BillDTO getById(Long id) {
        Bill bill = billRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        return mapToDTO(bill);
    }

    // ===== DELETE =====
    @Override
    @Transactional
    public void deleteBill(Long id) {
        billRepo.deleteById(id);
    }

    // ===== MAPPING LOGIC =====
    private BillDTO mapToDTO(Bill bill) {
        List<BillDetailDTO> items =
                (bill.getBillDetails() == null ? new ArrayList<BillDetail>() : bill.getBillDetails())
                        .stream()
                        .map(d -> new BillDetailDTO(
                                d.getProduct().getProductId(),
                                d.getProduct().getProductName(),
                                d.getQuantity(),
                                d.getPriceAtPurchase()
                        ))
                        .toList();

        return new BillDTO(
                bill.getBillId(),
                bill.getUser().getId(),
                bill.getAddress().getAddressId(),
                bill.getAddress().getDetailAddress(),
                bill.getTime(),
                bill.getStatus(),
                bill.getTotalMoney(),
                bill.getPaymentMethod(),
                bill.getQrCode(),
                items
        );
    }
}