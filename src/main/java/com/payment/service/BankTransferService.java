package com.payment.service;

import com.payment.entity.BankTransfer;
import com.payment.model.BankTransferModel;
import com.payment.repository.BankTransferRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankTransferService {

    private static final Logger logger =
            LogManager.getLogger(BankTransferService.class);

    private final BankTransferRepository repository;

    public BankTransferService(BankTransferRepository repository) {
        this.repository = repository;
    }

    public BankTransfer processTransfer(BankTransferModel model) {

        logger.info("Bank transfer processing started for userId: {}",
                model.getUserId());

        BankTransfer transfer = new BankTransfer();

        transfer.setUserId(model.getUserId());
        transfer.setSenderAccount(model.getSenderAccount());
        transfer.setReceiverAccount(model.getReceiverAccount());
        transfer.setIfsc(model.getIfsc());
        transfer.setAmount(model.getAmount());
        transfer.setPurpose(model.getPurpose());
        transfer.setTransferDate(LocalDateTime.now());

        if (model.getUserId() == null) {
            transfer.setStatus("FAILED");
            logger.error("Bank transfer failed: User ID is missing");

        } else if (model.getSenderAccount() == null
                || model.getSenderAccount().isBlank()) {
            transfer.setStatus("FAILED");
            logger.error("Bank transfer failed: Sender account is missing");

        } else if (model.getReceiverAccount() == null
                || model.getReceiverAccount().isBlank()) {
            transfer.setStatus("FAILED");
            logger.error("Bank transfer failed: Receiver account is missing");

        } else if (model.getIfsc() == null
                || model.getIfsc().isBlank()) {
            transfer.setStatus("FAILED");
            logger.error("Bank transfer failed: IFSC is missing");

        } else if (model.getAmount() == null || model.getAmount() <= 0) {
            transfer.setStatus("FAILED");
            logger.error("Bank transfer failed: Invalid amount");

        } else {
            transfer.setStatus("SUCCESS");
            logger.info("Bank transfer successful");
        }

        return repository.save(transfer);
    }

    public List<BankTransfer> getTransfers(Long userId) {

        logger.info("Fetching bank transfers for userId: {}", userId);

        return repository.findByUserId(userId);
    }
}