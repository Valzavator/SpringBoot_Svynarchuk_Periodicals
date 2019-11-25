package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.PaymentDao;
import com.gmail.maxsvynarchuk.persistence.entity.Payment;
import com.gmail.maxsvynarchuk.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Service responsible for processing payment-related operations
 *
 * @author Maksym Svynarchuk
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentDao paymentDao;

    public Optional<Payment> findPaymentById(Long id) {
        log.debug("Attempt to find payment by id");
        return paymentDao.findOne(id);
    }

    public Page<Payment> findAllPayments(Pageable pageable) {
        log.debug("Attempt to find all payments");
        return paymentDao.findAll(pageable);
    }

    public Payment createPayment(User user, BigDecimal totalPrice) {
        log.debug("Attempt to create payment");
        Payment payment = Payment.builder()
                .user(user)
                .totalPrice(totalPrice)
                .paymentDateTime(LocalDateTime.now())
                .build();
        return paymentDao.insert(payment);
    }
}