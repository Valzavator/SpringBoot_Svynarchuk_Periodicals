package com.gmail.maxsvynarchuk.persistence.entity;

import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "periodicals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Periodical implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long periodicalId;

    @NotNull
    private String periodicalName;

    @Digits(integer=10, fraction = 2)
    @NotNull
    private BigDecimal periodicalPrice;

    @NotNull
    private String periodicalDescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PeriodicalStatus periodicalStatus;

    @JoinColumn(name = "frequency_id")
    @ManyToOne
    private Frequency frequency;

    @ManyToOne
    @JoinColumn(name = "periodical_type_id")
    @NotNull
    private PeriodicalType periodicalType;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @NotNull
    private Publisher publisher;
}
