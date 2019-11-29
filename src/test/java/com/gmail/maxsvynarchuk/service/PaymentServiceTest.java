package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.PaymentDao;
import com.gmail.maxsvynarchuk.persistence.entity.Payment;
import com.gmail.maxsvynarchuk.persistence.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private PaymentDao paymentDao;

    @Test
    void findPaymentByIdWithExistingPaymentTest() {
        Long paymentId = 1L;
        Optional<Payment> expected = Optional.of(
                Payment.builder()
                .id(paymentId)
                .build());
        when(paymentDao.findOne(paymentId)).thenReturn(expected);

        Optional<Payment> actual = paymentService.findPaymentById(paymentId);

        assertEquals(expected, actual);
        verify(paymentDao, times(1)).findOne(paymentId);
    }

    @Test
    void findPaymentByIdWithNotExistingPaymentTest() {
        Long paymentId = 1L;
        when(paymentDao.findOne(paymentId)).thenReturn(Optional.empty());

        Optional<Payment> paymentOpt = paymentService.findPaymentById(paymentId);

        assertFalse(paymentOpt.isPresent());
        verify(paymentDao, times(1)).findOne(paymentId);
    }

    @Test
    void findAllPaymentsTest() {
        int page = 1;
        int size = 5;
        PageRequest pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.desc("paymentDateTime")));

        paymentService.findAllPayments(page, size);

        verify(paymentDao, times(1)).findAll(pageable);
    }

    @Test
    void findAllPaymentsWithNegativePageTest() {
        int page = -10;
        int size = 5;
        PageRequest pageable = PageRequest.of(0, size, Sort.by(
                Sort.Order.desc("paymentDateTime")));

        paymentService.findAllPayments(page, size);

        verify(paymentDao, times(1)).findAll(pageable);
    }

    @Test
    void createPayment() {
        Long paymentID = 1L;
        User user = User.builder()
                .id(1L)
                .build();
        BigDecimal totalPrice = new BigDecimal("10");
        when(paymentDao.insert(any(Payment.class))).then((Answer<Payment>) invocationOnMock -> {
            Payment payment = invocationOnMock.getArgument(0);
            payment.setId(paymentID);
            return payment;
        });

        Payment actual = paymentService.createPayment(user, totalPrice);

        assertEquals(paymentID, actual.getId());
        assertEquals(user, actual.getUser());
        assertEquals(totalPrice, actual.getTotalPrice());
        assertNotNull(actual.getPaymentDateTime());
        verify(paymentDao, times(1)).insert(actual);
    }
}