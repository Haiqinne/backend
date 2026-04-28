package com.bookstore.backend.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DashboardResponse {

    // ===== SUMMARY =====
    private long totalOrders;
    private long totalProducts;
    private long totalCustomers;
    private double totalRevenue;

    // ===== RECENT ORDERS =====
    private List<RecentOrder> recentOrders;

    // ===== ACTIVITIES =====
    private List<Activity> activities;

    // ===== INNER CLASS =====
    @Data
    @AllArgsConstructor
    public static class RecentOrder {
        private Long id;
        private String customerName;
        private int totalItems;
        private double totalMoney;
        private String status;
        private LocalDateTime time;
    }

    @Data
    @AllArgsConstructor
    public static class Activity {
        private String type;
        private String message;
        private String time;
    }
}