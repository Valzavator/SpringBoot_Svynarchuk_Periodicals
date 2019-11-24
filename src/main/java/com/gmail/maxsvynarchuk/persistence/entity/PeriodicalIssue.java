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
@Table(name = "periodical_issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodicalIssue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long periodicalIssueId;

    @NotNull
    private String issuesName;

    @NotNull
    private String issueNo;

    @NotNull
    private LocalDate publicationDate;

    private String issuesDescription;

    @ManyToOne
    @JoinColumn(name = "periodical_id")
    @NotNull
    private Periodical periodical;
}
