package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.FrequencyDao;
import com.gmail.maxsvynarchuk.persistence.dao.PeriodicalDao;
import com.gmail.maxsvynarchuk.persistence.dao.PeriodicalTypeDao;
import com.gmail.maxsvynarchuk.persistence.dao.PublisherDao;
import com.gmail.maxsvynarchuk.persistence.entity.Frequency;
import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.PeriodicalType;
import com.gmail.maxsvynarchuk.persistence.entity.Publisher;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Service responsible for processing periodical-related operations
 *
 * @author Maksym Svynarchuk
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PeriodicalService {
    private final PeriodicalDao periodicalDao;
    private final PeriodicalTypeDao periodicalTypeDao;
    private final FrequencyDao frequencyDao;
    private final PublisherDao publisherDao;

    public Periodical createPeriodical(Periodical periodical) {
        return periodicalDao.insert(periodical);
    }

    public void updatePeriodical(Periodical periodical) {
        log.debug("Attempt to update periodical");
        periodicalDao.update(periodical);
    }

    public void changeStatus(Periodical periodical, PeriodicalStatus newStatus) {
        log.debug("Attempt to change status of periodical");
        if (periodical.getPeriodicalStatus() != newStatus) {
            periodical.setPeriodicalStatus(newStatus);
            updatePeriodical(periodical);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Periodical> findPeriodicalById(Long id) {
        log.debug("Attempt to find periodical by id");
        return periodicalDao.findOne(id);
    }

    @Transactional(readOnly = true)
    public Page<Periodical> findAllPeriodicals(Pageable pageable) {
        log.debug("Attempt to find all periodicals");
        return periodicalDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Periodical> findAllPeriodicalsByStatus(PeriodicalStatus status,
                                                       Pageable pageable) {
        log.debug("Attempt to find all periodicals by status");
        return periodicalDao.findByStatus(status, pageable);
    }

    @Transactional(readOnly = true)
    public List<PeriodicalType> findAllPeriodicalTypes() {
        log.debug("Attempt to find all periodical types");
        return periodicalTypeDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Frequency> findAllFrequencies() {
        log.debug("Attempt to find all frequencies");
        return frequencyDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Publisher> findAllPublishers() {
        log.debug("Attempt to find all publishers");
        return publisherDao.findAll();
    }
}
