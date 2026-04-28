package com.bookstore.backend.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {

    private Long billId;
    private Long userId;
    private Long addressID;
    private String detailAddress;
    private LocalDateTime time;
    private String status;
    private Double totalMoney; // ✅ NEW

    private String paymentMethod; // ✅ NEW
    private String qrCode;        // ✅ NEW

    private List<BillDetailDTO> items;
}