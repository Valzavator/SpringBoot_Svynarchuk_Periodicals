package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PeriodicalDao extends GenericDao<Periodical, Long> {
    /**
     * Retrieves all periodicals associated with certain periodical status.
     *
     * @param status   status of periodical
     * @param pageable pageable
     * @return list of retrieved periodicals
     */
    Page<Periodical> findByStatus(PeriodicalStatus status, Pageable pageable);

    /**
     * Retrieves count of objects from database.
     *
     * @param status status of periodical
     * @return count of objects with same status.
     */
    long getCountByStatus(PeriodicalStatus status);
}
