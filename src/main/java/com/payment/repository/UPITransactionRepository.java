package com.payment.repository;

import com.payment.entity.UPITransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UPITransactionRepository
        extends JpaRepository<UPITransaction, Long> {

    List<UPITransaction> findByUserId(Long userId);
}