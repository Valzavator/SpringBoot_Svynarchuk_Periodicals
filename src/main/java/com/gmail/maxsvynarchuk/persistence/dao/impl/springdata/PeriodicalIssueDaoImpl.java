package com.gmail.maxsvynarchuk.persistence.dao.impl.springdata;

import com.gmail.maxsvynarchuk.persistence.dao.PeriodicalIssueDao;
import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.PeriodicalIssue;
import com.gmail.maxsvynarchuk.persistence.repository.PeriodicalIssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PeriodicalIssueDaoImpl implements PeriodicalIssueDao {
    private final PeriodicalIssueRepository repository;

    @Override
    public List<PeriodicalIssue> findByPeriodical(Periodical periodical) {
        return repository.findByPeriodicalOrderByPublicationDate(periodical);
    }

    @Override
    public boolean existByNumberAndPeriodical(String issueNumber, Periodical periodical) {
        return repository.existsPeriodicalIssueByIssueNumberAndPeriodical(issueNumber, periodical);
    }

    @Override
    public Optional<PeriodicalIssue> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<PeriodicalIssue> findAll() {
        return repository.findAll();
    }

    @Override
    public PeriodicalIssue insert(PeriodicalIssue obj) {
        return repository.save(obj);
    }

    @Override
    public void update(PeriodicalIssue obj) {
        repository.save(obj);
    }

    @Override
    public void delete(PeriodicalIssue obj) {
        repository.delete(obj);
    }
}
