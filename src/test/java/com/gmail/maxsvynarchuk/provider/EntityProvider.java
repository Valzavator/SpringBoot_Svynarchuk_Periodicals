package com.gmail.maxsvynarchuk.provider;

import com.gmail.maxsvynarchuk.persistence.entity.SubscriptionPlan;

import java.math.BigDecimal;

public class EntityProvider {
    public static SubscriptionPlan getOneMonthSubscriptionPlan() {
        return SubscriptionPlan.builder()
                .monthsAmount(1)
                .rate(new BigDecimal(1))
                .build();
    }

    public static SubscriptionPlan getThreeMonthSubscriptionPlan() {
        return SubscriptionPlan.builder()
                .monthsAmount(3)
                .rate(new BigDecimal("0.9"))
                .build();
    }

    public static SubscriptionPlan getSixMonthSubscriptionPlan() {
        return SubscriptionPlan.builder()
                .monthsAmount(6)
                .rate(new BigDecimal("0.8"))
                .build();
    }

    public static SubscriptionPlan getTwelveMonthSubscriptionPlan() {
        return SubscriptionPlan.builder()
                .monthsAmount(12)
                .rate(new BigDecimal("0.7"))
                .build();
    }
}
