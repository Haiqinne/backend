package com.bookstore.backend.controller;

import com.bookstore.backend.dto.BillDTO;
import com.bookstore.backend.dto.CreateBillRequest;
import com.bookstore.backend.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;
    // ✅ UPDATE STATUS
    @PutMapping("/{id}/status")
    public BillDTO updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        String status = request.get("status");
        return billService.updateStatus(id, status);
    }
    // ✅ Tạo bill
    @PostMapping
    public BillDTO create(@RequestBody CreateBillRequest request) {
        return billService.createBill(request);
    }
    // ✅ Lấy chi tiết 1 bill
    @GetMapping("/{id}")
    public BillDTO getById(@PathVariable Long id) {
        return billService.getById(id);
    }

    // ✅ Lấy bill theo user
    @GetMapping("/user/{userId}")
    public List<BillDTO> getByUser(@PathVariable Long userId) {
        return billService.getBillsByUser(userId);
    }

    // ✅ Lấy tất cả
    @GetMapping
    public List<BillDTO> getAll() {
        return billService.getAllBills();
    }

    // ✅ Xóa
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        billService.deleteBill(id);
    }
}