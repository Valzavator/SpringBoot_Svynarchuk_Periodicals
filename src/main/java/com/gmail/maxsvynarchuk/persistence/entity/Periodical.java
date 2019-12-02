package com.gmail.maxsvynarchuk.persistence.entity;

import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "periodicals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Periodical implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "periodical_id")
    private Long id;

    @Column(name = "periodical_name")
    @NotNull
    private String name;

    @Column(name = "periodical_price")
    @Digits(integer=10, fraction = 2)
    @NotNull
    private BigDecimal price;

    @Column(name = "periodical_description")
    @NotNull
    private String description;

    @Column(name = "periodical_status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private PeriodicalStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frequency_id")
    @NotNull
    private Frequency frequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodical_type_id")
    @NotNull
    private PeriodicalType periodicalType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    @NotNull
    private Publisher publisher;
}