package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.FrequencyDao;
import com.gmail.maxsvynarchuk.persistence.dao.PeriodicalDao;
import com.gmail.maxsvynarchuk.persistence.dao.PeriodicalTypeDao;
import com.gmail.maxsvynarchuk.persistence.dao.PublisherDao;
import com.gmail.maxsvynarchuk.persistence.entity.Frequency;
import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.PeriodicalType;
import com.gmail.maxsvynarchuk.persistence.entity.Publisher;
import com.gmail.maxsvynarchuk.service.exception.ServiceException;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
        log.debug("Attempt to create periodical");
        checkPeriodical(periodical);
        periodical.setStatus(PeriodicalStatus.ACTIVE);
        return periodicalDao.insert(periodical);
    }

    public void updatePeriodical(Periodical periodical) {
        log.debug("Attempt to update periodical");
        if (Objects.isNull(periodical.getId())) {
            throw new ServiceException("Periodicals ID cannot be null for the upgrade operation");
        }
        checkPeriodical(periodical);
        periodicalDao.update(periodical);
    }

    public void changeStatus(Periodical periodical, PeriodicalStatus newStatus) {
        log.debug("Attempt to change status of periodical");
        if (periodical.getStatus() != newStatus) {
            periodical.setStatus(newStatus);
            updatePeriodical(periodical);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Periodical> findPeriodicalById(Long id) {
        log.debug("Attempt to find periodical by id");
        return periodicalDao.findOne(id);
    }

    @Transactional(readOnly = true)
    public Page<Periodical> findAllPeriodicals(int page, int size) {
        log.debug("Attempt to find all periodicals");
        PageRequest pageable = PageRequest.of(Math.max(page, 0), size, Sort.by(
                Sort.Order.asc("status"),
                Sort.Order.desc("id")));
        return periodicalDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Periodical> findAllPeriodicalsByStatus(PeriodicalStatus status,
                                                       int page,
                                                       int size) {
        log.debug("Attempt to find all periodicals by status");
        PageRequest pageable = PageRequest.of(Math.max(page, 0), size, Sort.by(
                Sort.Order.asc("status"),
                Sort.Order.desc("id")));
        return periodicalDao.findByStatus(status, pageable);
    }

    @Transactional(readOnly = true)
    public List<PeriodicalType> findAllPeriodicalTypes() {
        log.debug("Attempt to find all periodical types");
        return periodicalTypeDao.findAll();
    }

    @Transactional(readOnly = true)
    public boolean existPeriodicalTypeById(Integer id) {
        log.debug("Trying to find out if periodical type exists by ID");
        return periodicalTypeDao.exist(id);
    }

    @Transactional(readOnly = true)
    public List<Frequency> findAllFrequencies() {
        log.debug("Attempt to find all frequencies");
        return frequencyDao.findAll();
    }

    @Transactional(readOnly = true)
    public boolean existFrequencyById(Integer id) {
        log.debug("Trying to find out if frequency exists by ID");
        return frequencyDao.exist(id);
    }

    @Transactional(readOnly = true)
    public List<Publisher> findAllPublishers() {
        log.debug("Attempt to find all publishers");
        return publisherDao.findAll();
    }

    @Transactional(readOnly = true)
    public boolean existPublisherById(Long id) {
        log.debug("Trying to find out if publisher exists by ID");
        return publisherDao.exist(id);
    }

    private void checkPeriodical(Periodical periodical) {
        if (!existPeriodicalTypeById(periodical.getPeriodicalType().getId()) ||
                !existFrequencyById(periodical.getFrequency().getId()) ||
                !existPublisherById(periodical.getPublisher().getId())) {
            throw new ServiceException("Such periodical type / frequency / publisher doesn't exist");
        }
    }
}
