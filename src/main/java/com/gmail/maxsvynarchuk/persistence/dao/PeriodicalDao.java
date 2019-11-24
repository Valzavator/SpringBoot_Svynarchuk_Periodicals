package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeriodicalDao extends GenericDao<Periodical, Long> {
    /**
     * Retrieves all periodicals associated with certain periodical status.
     *
     * @param status   status of periodical
     * @param pageable pageable
     * @return list of retrieved periodicals
     */
    Page<Periodical> findByStatus(PeriodicalStatus status, Pageable pageable);
}
