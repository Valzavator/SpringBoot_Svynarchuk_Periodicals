package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.SubscriptionDao;
import com.gmail.maxsvynarchuk.persistence.dao.SubscriptionPlanDao;
import com.gmail.maxsvynarchuk.persistence.entity.*;
import com.gmail.maxsvynarchuk.service.exception.ServiceException;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Service responsible for processing subscription-related operations
 *
 * @author Maksym Svynarchuk
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class SubscriptionService {
    private final SubscriptionDao subscriptionDao;
    private final SubscriptionPlanDao subscriptionPlanDao;
    private final PaymentService paymentService;
    private final PeriodicalService periodicalService;

    @Transactional(readOnly = true)
    public Page<Subscription> findAllSubscriptionsByUserAndStatus(User user,
                                                                  boolean isExpired,
                                                                  Pageable pageable) {
        log.debug("Attempt to find all subscriptions by user and status");
        return subscriptionDao.findByUserAndStatus(user, isExpired, pageable);
    }

    @Transactional(readOnly = true)
    public List<Subscription> findAllSubscriptionsByPayment(Payment payment) {
        log.debug("Attempt to find all subscriptions by payment");
        List<Subscription> subscriptions = subscriptionDao.findByPayment(payment);
        if (subscriptions.size() > 0) {
            return subscriptions;
        } else {
            log.error("Payment cannot exist without subscription: {}", payment);
            throw new ServiceException("Payment cannot exist without subscription!");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void processSubscriptions(User user,
                                     List<Subscription> subscriptions,
                                     BigDecimal totalPrice) {
        log.debug("Attempt to process subscriptions");
        if (subscriptions.size() != 0) {
            Payment payment = paymentService.createPayment(user, totalPrice);

            for (Subscription subscription : subscriptions) {
                Periodical periodical =
                        periodicalService.findPeriodicalById(
                                subscription.getPeriodical().getPeriodicalId())
                                .orElseThrow(() -> new ServiceException(
                                        "Subscription cannot refer to a non-existent periodical"));
                if (periodical.getPeriodicalStatus() == PeriodicalStatus.SUSPENDED) {
                    throw new ServiceException("Can't subscribe to periodical with SUSPEND status");
                }

                subscription.setPayment(payment);
                LocalDate startDate = payment.getPaymentDateTime().toLocalDate();
                LocalDate endDate = startDate.plusMonths(
                        subscription.getSubscriptionPlan().getMonthsAmount()
                );
                subscription.setStartDate(startDate);
                subscription.setEndDate(endDate);
                subscriptionDao.insert(subscription);
            }
        }
    }

    @Transactional(readOnly = true)
    public boolean isAlreadySubscribed(User user, Periodical periodical) {
        log.debug("Attempt to check that user is already subscribed");
        return subscriptionDao.isUserAlreadySubscribed(user, periodical);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionPlan> findAllSubscriptionPlans() {
        log.debug("Attempt to find all subscription plans");
        return subscriptionPlanDao.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<SubscriptionPlan> findSubscriptionPlanById(Integer id) {
        log.debug("Attempt to find subscription plan by id");
        return subscriptionPlanDao.findOne(id);
    }
}
