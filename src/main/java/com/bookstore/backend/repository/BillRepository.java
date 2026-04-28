package com.bookstore.backend.repository;

import com.bookstore.backend.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByUser_Id(Long userId);
    @Query("SELECT SUM(b.totalMoney) FROM Bill b")
    Double getTotalRevenue();

    @Query("SELECT b FROM Bill b ORDER BY b.time DESC")
    List<Bill> findRecentBills(org.springframework.data.domain.Pageable pageable);
}