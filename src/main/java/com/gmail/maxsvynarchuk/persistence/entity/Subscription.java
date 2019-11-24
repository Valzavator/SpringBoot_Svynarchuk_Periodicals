package com.gmail.maxsvynarchuk.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @NotNull
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "periodical_id")
    @NotNull
    private Periodical periodical;

    @ManyToOne
    @JoinColumn(name = "subscription_plan_id")
    @NotNull
    private SubscriptionPlan subscriptionPlan;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;
}
