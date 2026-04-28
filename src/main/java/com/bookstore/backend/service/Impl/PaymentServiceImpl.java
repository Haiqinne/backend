package com.bookstore.backend.service.Impl;

import com.bookstore.backend.entity.Bill;
import com.bookstore.backend.repository.BillRepository;
import com.bookstore.backend.service.PaymentService;
import com.bookstore.backend.service.SePayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final BillRepository billRepo;
    private final SePayService sePayService; // ✅ thêm dòng này

    @Override
    public boolean checkPaid(Long billId, Double amount) {

        boolean isPaid = sePayService.checkPayment(billId, amount);

        if (!isPaid) {
            return false;
        }

        Optional<Bill> opt = billRepo.findById(billId);

        if (opt.isEmpty()) {
            throw new RuntimeException("Bill không tồn tại: " + billId);
        }

        Bill bill = opt.get();

        // ✅ tránh update nhiều lần
        if (!"PAID".equals(bill.getStatus())) {
            bill.setStatus("PAID");
            billRepo.save(bill);
        }

        return true;
    }
}