package com.payment.repository;

import com.payment.entity.BankTransfer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransferRepository
        extends JpaRepository<BankTransfer, Long> {

    List<BankTransfer> findByUserId(Long userId);
}