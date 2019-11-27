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
@Table(name = "periodical_issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodicalIssue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "periodical_issue_id")
    private Long id;

    @Column(name = "issues_name")
    @NotNull
    private String name;

    @Column(name = "issue_no")
    @NotNull
    private String issueNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate publicationDate;

    @Column(name = "issues_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "periodical_id")
    @NotNull
    private Periodical periodical;
}
