package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.SubscriptionDao;
import com.gmail.maxsvynarchuk.persistence.dao.SubscriptionPlanDao;
import com.gmail.maxsvynarchuk.persistence.entity.*;
import com.gmail.maxsvynarchuk.provider.EntityProvider;
import com.gmail.maxsvynarchuk.service.exception.ServiceException;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SubscriptionServiceTest {
    @InjectMocks
    private SubscriptionService subscriptionService;
    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock
    private SubscriptionPlanDao subscriptionPlanDao;
    @Mock
    private PaymentService paymentService;
    @Mock
    private PeriodicalService periodicalService;

    @Test
    void findAllSubscriptionsByUserAndStatusTest() {
        int page = 0;
        int size = 5;
        final boolean isExpired = false;
        User user = User.builder()
                .id(1L)
                .build();
        List<Subscription> subscriptions = new ArrayList<Subscription>() {{
            add(Subscription.builder().id(1L).build());
            add(Subscription.builder().id(2L).build());
            add(Subscription.builder().id(3L).build());
        }};
        Page<Subscription> expected = new PageImpl<>(subscriptions);
        PageRequest pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.asc("endDate")));
        when(subscriptionDao.findByUserAndStatus(user, isExpired, pageable))
                .thenReturn(expected);

        Page<Subscription> actual =
                subscriptionService.findAllSubscriptionsByUserAndStatus(user, isExpired, page, size);

        assertEquals(3, actual.getContent().size());
        verify(subscriptionDao, times(1))
                .findByUserAndStatus(user, isExpired, pageable);
    }

    @Test
    void findAllSubscriptionsByPaymentWithExistingSubscriptionsTest() {
        Payment payment = Payment.builder()
                .id(1L)
                .build();
        List<Subscription> expected = new ArrayList<Subscription>() {{
            add(Subscription.builder().id(1L).build());
            add(Subscription.builder().id(2L).build());
            add(Subscription.builder().id(3L).build());
        }};
        Sort sort = Sort.by(Sort.Order.asc("endDate"));
        when(subscriptionDao.findByPayment(payment, sort))
                .thenReturn(expected);

        List<Subscription> actual =
                subscriptionService.findAllSubscriptionsByPayment(payment);

        assertEquals(3, actual.size());
        verify(subscriptionDao, times(1)).findByPayment(payment, sort);
    }

    @Test
    void findAllSubscriptionsByPaymentWithNotExistingSubscriptionsTest() {
        Payment payment = Payment.builder()
                .id(1L)
                .build();
        Sort sort = Sort.by(Sort.Order.asc("endDate"));
        when(subscriptionDao.findByPayment(payment, sort))
                .thenReturn(Collections.emptyList());

        assertThrows(ServiceException.class, () ->
                subscriptionService.findAllSubscriptionsByPayment(payment));
        verify(subscriptionDao, times(1)).findByPayment(payment, sort);
    }

    @Test
    void processSubscriptionsTest() {
        User user = User.builder()
                .id(1L)
                .build();
        List<Subscription> subscriptions = new ArrayList<Subscription>() {{
            add(Subscription.builder()
                    .id(1L)
                    .periodical(Periodical.builder().id(1L).build())
                    .subscriptionPlan(EntityProvider.getOneMonthSubscriptionPlan())
                    .build());
            add(Subscription.builder()
                    .id(2L)
                    .periodical(Periodical.builder().id(2L).build())
                    .subscriptionPlan(EntityProvider.getTwelveMonthSubscriptionPlan())
                    .build());
        }};
        BigDecimal totalPrice = new BigDecimal("10");
        Payment payment = Payment.builder()
                .user(user)
                .totalPrice(totalPrice)
                .paymentDateTime(LocalDateTime.now())
                .build();
        when(paymentService.createPayment(user, totalPrice)).thenReturn(payment);
        when(periodicalService.findPeriodicalById(1L))
                .thenReturn(Optional.of(subscriptions.get(0).getPeriodical()));
        when(periodicalService.findPeriodicalById(2L))
                .thenReturn(Optional.of(subscriptions.get(1).getPeriodical()));

        subscriptionService.processSubscriptions(user, subscriptions, totalPrice);

        assertEquals(payment, subscriptions.get(0).getPayment());
        assertEquals(payment, subscriptions.get(1).getPayment());
        assertNotNull(subscriptions.get(0).getStartDate());
        assertNotNull(subscriptions.get(1).getStartDate());
        LocalDate endDate0 = subscriptions.get(0).getStartDate()
                .plusMonths(subscriptions.get(0).getSubscriptionPlan().getMonthsAmount());
        LocalDate endDate1 = subscriptions.get(1).getStartDate()
                .plusMonths(subscriptions.get(1).getSubscriptionPlan().getMonthsAmount());
        assertEquals(endDate0, subscriptions.get(0).getEndDate());
        assertEquals(endDate1, subscriptions.get(1).getEndDate());

        verify(paymentService, times(1)).createPayment(user, totalPrice);
        verify(periodicalService, times(2)).findPeriodicalById(anyLong());
        verify(subscriptionDao, times(2)).insert(any(Subscription.class));
    }

    @Test
    void processSubscriptionsWithInvalidPeriodicalTest() {
        User user = User.builder()
                .id(1L)
                .build();
        List<Subscription> subscriptions = new ArrayList<Subscription>() {{
            add(Subscription.builder()
                    .id(1L)
                    .periodical(Periodical.builder().id(1L).build())
                    .subscriptionPlan(EntityProvider.getOneMonthSubscriptionPlan())
                    .build());
        }};
        BigDecimal totalPrice = new BigDecimal("10");
        Payment payment = Payment.builder()
                .user(user)
                .totalPrice(totalPrice)
                .paymentDateTime(LocalDateTime.now())
                .build();
        when(paymentService.createPayment(user, totalPrice)).thenReturn(payment);
        when(periodicalService.findPeriodicalById(1L))
                .thenReturn(Optional.empty());

        Throwable serviceException = assertThrows(ServiceException.class, () ->
                subscriptionService.processSubscriptions(user, subscriptions, totalPrice));
        assertEquals("Subscription cannot refer to a non-existent periodical",
                serviceException.getMessage());
    }

    @Test
    void processSubscriptionsWithSuspendedPeriodicalTest() {
        User user = User.builder()
                .id(1L)
                .build();
        List<Subscription> subscriptions = new ArrayList<Subscription>() {{
            add(Subscription.builder()
                    .id(1L)
                    .periodical(Periodical.builder().id(1L).status(PeriodicalStatus.SUSPENDED).build())
                    .subscriptionPlan(EntityProvider.getOneMonthSubscriptionPlan())
                    .build());
        }};
        BigDecimal totalPrice = new BigDecimal("10");
        Payment payment = Payment.builder()
                .user(user)
                .totalPrice(totalPrice)
                .paymentDateTime(LocalDateTime.now())
                .build();
        when(paymentService.createPayment(user, totalPrice)).thenReturn(payment);
        when(periodicalService.findPeriodicalById(1L))
                .thenReturn(Optional.of(subscriptions.get(0).getPeriodical()));

        Throwable serviceException = assertThrows(ServiceException.class, () ->
                subscriptionService.processSubscriptions(user, subscriptions, totalPrice));
        assertEquals("Can't subscribe to periodical with SUSPEND status",
                serviceException.getMessage());
    }

    @Test
    void processSubscriptionsWithEmptySubscriptionsTest() {
        User user = User.builder()
                .id(1L)
                .build();
        List<Subscription> subscriptions = Collections.emptyList();
        BigDecimal totalPrice = new BigDecimal("10");

        subscriptionService.processSubscriptions(user, subscriptions, totalPrice);

        verify(paymentService, never()).createPayment(user, totalPrice);
        verify(periodicalService, never()).findPeriodicalById(anyLong());
        verify(subscriptionDao, never()).insert(any(Subscription.class));
    }

    @Test
    void isAlreadySubscribed() {
        final boolean isSubscribed = false;
        User user = User.builder()
                .id(1L)
                .build();
        Periodical periodical = Periodical.builder()
                .id(1L)
                .build();
        when(subscriptionDao.isUserAlreadySubscribed(user, periodical)).thenReturn(isSubscribed);

        assertFalse(subscriptionService.isAlreadySubscribed(user, periodical));
    }

    @Test
    void findAllSubscriptionPlans() {
        List<SubscriptionPlan> expected = new ArrayList<SubscriptionPlan>() {{
            add(EntityProvider.getOneMonthSubscriptionPlan());
            add(EntityProvider.getThreeMonthSubscriptionPlan());
            add(EntityProvider.getSixMonthSubscriptionPlan());
            add(EntityProvider.getTwelveMonthSubscriptionPlan());
        }};
        when(subscriptionPlanDao.findAll()).thenReturn(expected);

        List<SubscriptionPlan> actual = subscriptionService.findAllSubscriptionPlans();

        assertEquals(4, actual.size());
        verify(subscriptionPlanDao, times(1)).findAll();
    }

    @Test
    void findSubscriptionPlanByIdWithExistingSubscriptionPlanTest() {
        Integer subscriptionPlanId = 1;
        Optional<SubscriptionPlan> expected = Optional.of(
                SubscriptionPlan.builder()
                        .id(subscriptionPlanId)
                        .build());
        when(subscriptionPlanDao.findOne(subscriptionPlanId)).thenReturn(expected);

        Optional<SubscriptionPlan> actual =
                subscriptionService.findSubscriptionPlanById(subscriptionPlanId);

        assertEquals(expected, actual);
        verify(subscriptionPlanDao, times(1)).findOne(subscriptionPlanId);
    }

    @Test
    void findSubscriptionPlanByIdWithNotExistingSubscriptionPlanTest() {
        Integer subscriptionPlanId = 1;
        when(subscriptionPlanDao.findOne(subscriptionPlanId)).thenReturn(Optional.empty());

        Optional<SubscriptionPlan> periodicalOpt =
                subscriptionService.findSubscriptionPlanById(subscriptionPlanId);

        assertFalse(periodicalOpt.isPresent());
        verify(subscriptionPlanDao, times(1)).findOne(subscriptionPlanId);
    }
}