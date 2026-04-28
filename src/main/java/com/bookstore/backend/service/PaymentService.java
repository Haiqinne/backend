package com.bookstore.backend.service;

public interface PaymentService {
    boolean checkPaid(Long billId, Double amount);
}