package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.DashboardResponse;
import com.bookstore.backend.entity.*;
import com.bookstore.backend.repository.*;
import com.bookstore.backend.service.DashboardService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final BillRepository billRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    @Override
    public DashboardResponse getDashboard() {

        DashboardResponse res = new DashboardResponse();

        // ===== 1. SUMMARY =====
        res.setTotalOrders(billRepo.count());
        res.setTotalProducts(productRepo.count());
        res.setTotalCustomers(userRepo.count());
        res.setTotalRevenue(
                Optional.ofNullable(billRepo.getTotalRevenue()).orElse(0.0)
        );

        // ===== 2. RECENT ORDERS =====
        List<DashboardResponse.RecentOrder> orders =
                billRepo.findRecentBills(PageRequest.of(0, 5))
                        .stream()
                        .map(b -> new DashboardResponse.RecentOrder(
                                b.getBillId(),
                                b.getUser().getUsername(),
                                b.getBillDetails() != null ? b.getBillDetails().size() : 0,
                                b.getTotalMoney(),
                                b.getStatus(),
                                b.getTime()
                        ))
                        .toList();

        res.setRecentOrders(orders);

        // ===== 3. ACTIVITIES =====
        List<DashboardResponse.Activity> activities = new ArrayList<>();

        // 3.1 đơn hàng mới
        billRepo.findRecentBills(PageRequest.of(0, 3)).forEach(b -> {
            activities.add(new DashboardResponse.Activity(
                    "ORDER",
                    "Đơn hàng #" + b.getBillId() + " đã được tạo",
                    formatTimeAgo(b.getTime())
            ));
        });

        // 3.2 user mới
        userRepo.findAll(PageRequest.of(0, 2, Sort.by("id").descending()))
                .getContent()
                .forEach(u -> {
                    activities.add(new DashboardResponse.Activity(
                            "USER",
                            "User " + u.getUsername() + " vừa đăng ký",
                            "gần đây"
                    ));
                });

        // 3.3 sản phẩm sắp hết
        productRepo.findAll().stream()
                .filter(p -> p.getStock() != null && p.getStock() < 5)
                .limit(1)
                .forEach(p -> {
                    activities.add(new DashboardResponse.Activity(
                            "PRODUCT",
                            "Sản phẩm " + p.getProductName() + " sắp hết hàng",
                            "vừa xong"
                    ));
                });

        res.setActivities(activities);

        return res;
    }

    // ===== format time =====
    private String formatTimeAgo(LocalDateTime time) {
        long minutes = ChronoUnit.MINUTES.between(time, LocalDateTime.now());
        if (minutes < 60) return minutes + " phút trước";

        long hours = ChronoUnit.HOURS.between(time, LocalDateTime.now());
        if (hours < 24) return hours + " giờ trước";

        long days = ChronoUnit.DAYS.between(time, LocalDateTime.now());
        return days + " ngày trước";
    }
}