package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.PeriodicalIssueDao;
import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.PeriodicalIssue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Intermediate layer between command layer and dao layer.
 * Service responsible for processing issue-related operations
 *
 * @author Maksym Svynarchuk
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class IssueService {
    private final PeriodicalIssueDao periodicalIssueDao;

    public boolean addIssueToPeriodical(Periodical periodical, PeriodicalIssue periodicalIssue) {
        log.debug("Attempt to add issue to periodical");
        boolean issueIsPresent = periodicalIssueDao.existByNumberAndPeriodical(
                periodicalIssue.getIssueNumber(),
                periodical);
        if (!issueIsPresent) {
            periodicalIssue.setPeriodical(periodical);
            periodicalIssueDao.insert(periodicalIssue);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<PeriodicalIssue> findAllIssuesByPeriodical(Periodical periodical) {
        log.debug("Attempt to find all issues by periodical");
        return periodicalIssueDao.findByPeriodical(periodical);
    }
}
