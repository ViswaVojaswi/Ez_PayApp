package com.payment.controller;

import com.payment.entity.BankTransfer;
import com.payment.entity.UPITransaction;
import com.payment.model.BankTransferModel;
import com.payment.model.UPIPaymentModel;
import com.payment.service.BankTransferService;
import com.payment.service.UPITransactionService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final UPITransactionService upiService;
    private final BankTransferService bankService;

    public PaymentController(
            UPITransactionService upiService,
            BankTransferService bankService) {

        this.upiService = upiService;
        this.bankService = bankService;
    }

    // UPI Payment
    @PostMapping("/upi")
    public UPITransaction upiPayment(
            @RequestBody UPIPaymentModel model) {

        return upiService.processPayment(model);
    }

    // Bank Transfer
    @PostMapping("/bank")
    public BankTransfer bankTransfer(
            @RequestBody BankTransferModel model) {

        return bankService.processTransfer(model);
    }

    // UPI Transaction History
    @GetMapping("/upi/{userId}")
    public List<UPITransaction> getUPITransactions(
            @PathVariable Long userId) {

        return upiService.getTransactions(userId);
    }

    // Bank Transfer History
    @GetMapping("/bank/{userId}")
    public List<BankTransfer> getBankTransfers(
            @PathVariable Long userId) {

        return bankService.getTransfers(userId);
    }
}