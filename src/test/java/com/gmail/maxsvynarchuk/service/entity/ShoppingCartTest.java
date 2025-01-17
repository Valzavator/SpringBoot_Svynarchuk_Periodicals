package com.gmail.maxsvynarchuk.service.entity;

import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.Subscription;
import com.gmail.maxsvynarchuk.provider.EntityProvider;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {
    private ShoppingCart shoppingCart;

    @BeforeEach
    private void setUp() {
        shoppingCart = new ShoppingCart();
    }

    @Test
    void addItemTest() {
        Periodical periodical = Periodical.builder()
                .id(1L)
                .build();
        Subscription subscription = Subscription.builder()
                .periodical(periodical)
                .build();

        assertEquals(0, shoppingCart.size());
        assertTrue(shoppingCart.addItem(subscription));
        assertEquals(1, shoppingCart.size());
        assertFalse(shoppingCart.addItem(subscription));
        assertEquals(1, shoppingCart.size());
    }

    @Test
    void removeItemTest() {
        long id = 1L;
        Periodical periodical = Periodical.builder()
                .id(id)
                .build();
        Subscription subscription = Subscription.builder()
                .periodical(periodical)
                .build();

        assertTrue(shoppingCart.addItem(subscription));
        assertEquals(1, shoppingCart.size());
        shoppingCart.removeItem(id);
        assertEquals(0, shoppingCart.size());
    }

    @Test
    void removeItemInvalidIdTest() {
        long id = 1L;
        Periodical periodical = Periodical.builder()
                .id(id)
                .build();
        Subscription subscription = Subscription.builder()
                .periodical(periodical)
                .build();

        assertTrue(shoppingCart.addItem(subscription));
        assertEquals(1, shoppingCart.size());

        shoppingCart.removeItem(999);

        assertEquals(1, shoppingCart.size());
    }

    @Test
    void getTotalPriceTest() {
        Periodical periodical1 = Periodical.builder()
                .id(1L)
                .price(new BigDecimal("1"))
                .build();
        Periodical periodical2 = Periodical.builder()
                .id(2L)
                .price(new BigDecimal("2"))
                .build();
        Periodical periodical3 = Periodical.builder()
                .id(3L)
                .price(new BigDecimal("3"))
                .build();
        Periodical periodical4 = Periodical.builder()
                .id(4L)
                .price(new BigDecimal("4"))
                .build();
        List<Subscription> subscriptions = new ArrayList<Subscription>() {{
            add(Subscription.builder()
                    .periodical
                            (periodical1)
                    .subscriptionPlan(EntityProvider.getOneMonthSubscriptionPlan())
                    .build());
            add(Subscription.builder()
                    .periodical
                            (periodical2)
                    .subscriptionPlan(EntityProvider.getThreeMonthSubscriptionPlan())
                    .build());
            add(Subscription.builder()
                    .periodical
                            (periodical3)
                    .subscriptionPlan(EntityProvider.getSixMonthSubscriptionPlan())
                    .build());
            add(Subscription.builder()
                    .periodical
                            (periodical4)
                    .subscriptionPlan(EntityProvider.getTwelveMonthSubscriptionPlan())
                    .build());
        }};

        BigDecimal expectedTotalPrice = new BigDecimal("1");
        shoppingCart.addItem(subscriptions.get(0));
        assertEquals(0, expectedTotalPrice.compareTo(
                shoppingCart.getTotalPrice()));

        expectedTotalPrice = new BigDecimal("6.4");
        shoppingCart.addItem(subscriptions.get(1));
        assertEquals(0, expectedTotalPrice.compareTo(
                shoppingCart.getTotalPrice()));

        expectedTotalPrice = new BigDecimal("20.8");
        shoppingCart.addItem(subscriptions.get(2));
        assertEquals(0, expectedTotalPrice.compareTo(
                shoppingCart.getTotalPrice()));

        expectedTotalPrice = new BigDecimal("54.40");
        shoppingCart.addItem(subscriptions.get(3));
        assertEquals(0, expectedTotalPrice.compareTo(
                shoppingCart.getTotalPrice()));
    }

    @Test
    void isHasSuspendedPeriodicalTest() {
        Periodical activePeriodical = Periodical.builder()
                .id(1L)
                .status(PeriodicalStatus.ACTIVE)
                .build();
        Periodical suspendedPeriodical = Periodical.builder()
                .id(2L)
                .status(PeriodicalStatus.SUSPENDED)
                .build();
        Subscription subscriptionWithActive = Subscription.builder()
                .periodical
                        (activePeriodical)
                .subscriptionPlan(EntityProvider.getOneMonthSubscriptionPlan())
                .build();
        Subscription subscriptionWithSuspended = Subscription.builder()
                .periodical
                        (suspendedPeriodical)
                .subscriptionPlan(EntityProvider.getOneMonthSubscriptionPlan())
                .build();

        shoppingCart.addItem(subscriptionWithActive);
        assertFalse(shoppingCart.isHasSuspendedPeriodical());

        shoppingCart.addItem(subscriptionWithSuspended);
        assertTrue(shoppingCart.isHasSuspendedPeriodical());
    }

    @Test
    void getItemsTest() {
        Periodical periodical1 = Periodical.builder()
                .id(1L)
                .build();
        Periodical periodical2 = Periodical.builder()
                .id(2L)
                .build();
        List<Subscription> expected = new ArrayList<Subscription>() {{
            add(Subscription.builder()
                    .periodical
                            (periodical1)
                    .build());
            add(Subscription.builder()
                    .periodical
                            (periodical2)
                    .build());
        }};

        shoppingCart.addItem(expected.get(0));
        shoppingCart.addItem(expected.get(1));

        List<Subscription> actual = shoppingCart.getItems();

        assertNotSame(expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateItemWithSameItemTest() {
        Periodical periodical = Periodical.builder()
                .id(1L)
                .status(PeriodicalStatus.ACTIVE)
                .build();
        Periodical samePeriodicalWithUpdatedStatus = Periodical.builder()
                .id(1L)
                .status(PeriodicalStatus.SUSPENDED)
                .build();
        Subscription subscriptionWithActive = Subscription.builder()
                .periodical
                        (periodical)
                .build();
        Subscription sameSubscriptionWithSuspended = Subscription.builder()
                .periodical
                        (samePeriodicalWithUpdatedStatus)
                .build();

        shoppingCart.addItem(subscriptionWithActive);
        shoppingCart.updateItem(sameSubscriptionWithSuspended);

        List<Subscription> expected = new ArrayList<Subscription>() {{
            add(sameSubscriptionWithSuspended);
        }};
        List<Subscription> actual = shoppingCart.getItems();

        assertNotSame(subscriptionWithActive, sameSubscriptionWithSuspended);
        assertNotSame(expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateItemWithAnotherItemTest() {
        Periodical periodical = Periodical.builder()
                .id(1L)
                .status(PeriodicalStatus.ACTIVE)
                .build();
        Periodical anotherPeriodicalWithUpdatedStatus = Periodical.builder()
                .id(2L)
                .status(PeriodicalStatus.SUSPENDED)
                .build();
        Subscription subscriptionWithActive = Subscription.builder()
                .periodical
                        (periodical)
                .build();
        Subscription anotherSubscriptionWithSuspended = Subscription.builder()
                .periodical
                        (anotherPeriodicalWithUpdatedStatus)
                .build();

        shoppingCart.addItem(subscriptionWithActive);
        shoppingCart.updateItem(anotherSubscriptionWithSuspended);

        List<Subscription> expected = new ArrayList<Subscription>() {{
            add(subscriptionWithActive);
        }};
        List<Subscription> actual = shoppingCart.getItems();

        assertNotSame(subscriptionWithActive, anotherSubscriptionWithSuspended);
        assertNotSame(expected, actual);
        assertEquals(expected, actual);
    }
}
