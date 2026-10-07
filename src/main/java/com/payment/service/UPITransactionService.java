package com.payment.service;

import com.payment.entity.UPITransaction;
import com.payment.model.UPIPaymentModel;
import com.payment.repository.UPITransactionRepository;
import com.payment.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UPITransactionService {

    private static final Logger logger =
            LogManager.getLogger(UPITransactionService.class);

    private final UPITransactionRepository repository;

    public UPITransactionService(UPITransactionRepository repository) {
        this.repository = repository;
    }

    public UPITransaction processPayment(UPIPaymentModel model) {

        logger.info("UPI payment processing started for userId: {}",
                model.getUserId());

        UPITransaction transaction = new UPITransaction();

        transaction.setUserId(model.getUserId());
        transaction.setUpiId(model.getUpiId());
        transaction.setAmount(model.getAmount());
        transaction.setNote(model.getNote());
        transaction.setTransactionDate(LocalDateTime.now());

        if (model.getUserId() == null) {
            transaction.setStatus("FAILED");
            logger.error("UPI payment failed: User ID is missing");

        } else if (model.getUpiId() == null || model.getUpiId().isBlank()) {
            transaction.setStatus("FAILED");
            logger.error("UPI payment failed: UPI ID is missing");

        } else if (model.getAmount() == null || model.getAmount() <= 0) {
            transaction.setStatus("FAILED");
            logger.error("UPI payment failed: Invalid amount");

        } else {
            transaction.setStatus("SUCCESS");
            logger.info("UPI payment successful");
        }
        
        return repository.save(transaction);
        
    }

    public List<UPITransaction> getTransactions(Long userId) {

        logger.info("Fetching UPI transactions for userId: {}", userId);

        return repository.findByUserId(userId);
    }
}