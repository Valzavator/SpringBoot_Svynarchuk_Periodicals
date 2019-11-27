package com.gmail.maxsvynarchuk.persistence.repository;

import com.gmail.maxsvynarchuk.persistence.entity.Payment;
import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.Subscription;
import com.gmail.maxsvynarchuk.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByPaymentOrderByEndDateAsc(Payment payment);

    Page<Subscription> findByUserAndEndDateBefore(User user, LocalDate date, Pageable pageable);

    Page<Subscription> findByUserAndEndDateGreaterThanEqual(User user, LocalDate date, Pageable pageable);

//    @Modifying
//    @Query("")
    boolean existsByUserAndPeriodicalAndEndDateIsGreaterThanEqual(
            User user,
            Periodical periodical,
            LocalDate currDate);
}
