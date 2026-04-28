package com.bookstore.backend.controller;

import com.bookstore.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/check/{billId}")
    public boolean checkPayment(@PathVariable Long billId,
                                @RequestParam Double amount) {
        return paymentService.checkPaid(billId, amount);
    }
}