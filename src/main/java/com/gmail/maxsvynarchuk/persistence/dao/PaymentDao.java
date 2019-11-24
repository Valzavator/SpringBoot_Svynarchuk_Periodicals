package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentDao extends GenericDao<Payment, Long> {

    Page<Payment> findAll(Pageable pageable);

}
