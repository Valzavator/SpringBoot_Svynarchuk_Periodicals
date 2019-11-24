package com.gmail.maxsvynarchuk.persistence.repository;

import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.PeriodicalIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeriodicalIssueRepository extends JpaRepository<PeriodicalIssue, Long> {

    List<PeriodicalIssue> findByPeriodicalOrderByPublicationDate(Periodical periodical);

    boolean existsPeriodicalIssueByIssueNoAndPeriodical(String issueNumber, Periodical periodical);

}
