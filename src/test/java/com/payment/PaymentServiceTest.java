package com.payment;

import com.payment.entity.UPITransaction;
import com.payment.model.UPIPaymentModel;
import com.payment.repository.UPITransactionRepository;
import com.payment.service.UPITransactionService;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    @Test
    void testUPIPaymentSuccess() {

        UPITransactionRepository repository =
                Mockito.mock(UPITransactionRepository.class);

        UPITransactionService service =
                new UPITransactionService(repository);

        UPIPaymentModel model = new UPIPaymentModel();

        model.setUserId(1L);
        model.setUpiId("receiver@upi");
        model.setAmount(500.0);
        model.setNote("Shopping");

        UPITransaction transaction =
                new UPITransaction();

        transaction.setUserId(1L);
        transaction.setUpiId("receiver@upi");
        transaction.setAmount(500.0);
        transaction.setNote("Shopping");
        transaction.setStatus("SUCCESS");

        Mockito.when(repository.save(Mockito.any(UPITransaction.class)))
                .thenReturn(transaction);

        UPITransaction result =
                service.processPayment(model);

        assertEquals("SUCCESS", result.getStatus());
        assertEquals(500.0, result.getAmount());
    }
}