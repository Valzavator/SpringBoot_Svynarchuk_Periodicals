package com.gmail.maxsvynarchuk.persistence.repository;

import com.gmail.maxsvynarchuk.persistence.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Page<Payment> findAllByOrderByPaymentDateDesc(Pageable pageable);

}
