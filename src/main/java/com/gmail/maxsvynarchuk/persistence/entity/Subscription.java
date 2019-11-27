package com.gmail.maxsvynarchuk.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
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
    @Column(name = "subscription_id")
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
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
