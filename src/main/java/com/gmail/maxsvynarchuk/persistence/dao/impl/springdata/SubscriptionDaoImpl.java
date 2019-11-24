package com.gmail.maxsvynarchuk.persistence.dao.impl.springdata;

import com.gmail.maxsvynarchuk.persistence.dao.SubscriptionDao;
import com.gmail.maxsvynarchuk.persistence.entity.Payment;
import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.Subscription;
import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.persistence.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SubscriptionDaoImpl implements SubscriptionDao {
    private final SubscriptionRepository repository;

    @Override
    public boolean isUserAlreadySubscribed(User user, Periodical periodical) {
        return repository.existsByUserAndPeriodical(user, periodical);
    }

    @Override
    public Page<Subscription> findByUserAndStatus(User user, boolean isExpired, Pageable pageable) {
        return isExpired
                ? repository.findByUserAndEndDateBefore(user, LocalDate.now(), pageable)
                : repository.findByUserAndEndDateGreaterThanEqual(user, LocalDate.now(), pageable);
    }

    @Override
    public List<Subscription> findByPayment(Payment payment) {
        return repository.findByPaymentOrderByEndDateAsc(payment);
    }

    @Override
    public Optional<Subscription> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Subscription> findAll() {
        return repository.findAll();
    }

    @Override
    public Subscription insert(Subscription obj) {
        return repository.save(obj);
    }

    @Override
    public void update(Subscription obj) {
        repository.save(obj);
    }

    @Override
    public void delete(Subscription obj) {
        repository.delete(obj);
    }
}
