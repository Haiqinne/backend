package com.bookstore.backend.service;

import com.bookstore.backend.dto.BillDTO;
import com.bookstore.backend.dto.CreateBillRequest;

import java.util.List;

public interface BillService {

    BillDTO createBill(CreateBillRequest request);

    BillDTO updateStatus(Long billId, String status);

    List<BillDTO> getBillsByUser(Long userId);

    List<BillDTO> getAllBills();

    void deleteBill(Long id);

    BillDTO getById(Long id);
}