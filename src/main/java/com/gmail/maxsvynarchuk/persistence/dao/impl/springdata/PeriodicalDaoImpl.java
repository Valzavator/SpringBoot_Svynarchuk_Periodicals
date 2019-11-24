package com.gmail.maxsvynarchuk.persistence.dao.impl.springdata;

import com.gmail.maxsvynarchuk.persistence.dao.PeriodicalDao;
import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.repository.PeriodicalRepository;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PeriodicalDaoImpl implements PeriodicalDao {
    private final PeriodicalRepository repository;

    @Override
    public Page<Periodical> findByStatus(PeriodicalStatus status, Pageable pageable) {
        return repository.findByPeriodicalStatus(status, pageable);
    }

    @Override
    public long getCountByStatus(PeriodicalStatus status) {
        return repository.countByPeriodicalStatus(status);
    }

    @Override
    public Optional<Periodical> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Periodical> findAll() {
        return repository.findAll();
    }

    @Override
    public Periodical insert(Periodical obj) {
        return repository.save(obj);
    }

    @Override
    public void update(Periodical obj) {
        repository.save(obj);
    }

    @Override
    public void delete(Periodical obj) {
        repository.delete(obj);
    }
}
