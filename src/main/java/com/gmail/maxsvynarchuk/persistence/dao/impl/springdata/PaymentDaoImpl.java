package com.gmail.maxsvynarchuk.persistence.dao.impl.springdata;

import com.gmail.maxsvynarchuk.persistence.dao.PaymentDao;
import com.gmail.maxsvynarchuk.persistence.entity.Payment;
import com.gmail.maxsvynarchuk.persistence.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PaymentDaoImpl implements PaymentDao {
    private final PaymentRepository repository;

    @Override
    public Optional<Payment> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Payment> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return repository.findAllByOrderByPaymentDateDesc(pageable);
    }

    @Override
    public Payment insert(Payment obj) {
        return repository.save(obj);
    }

    @Override
    public void update(Payment obj) {
        repository.save(obj);
    }

    @Override
    public void delete(Payment obj) {
        repository.delete(obj);
    }
}
