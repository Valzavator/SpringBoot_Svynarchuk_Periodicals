package com.gmail.maxsvynarchuk.persistence.dao.impl.springdata;

import com.gmail.maxsvynarchuk.persistence.dao.FrequencyDao;
import com.gmail.maxsvynarchuk.persistence.entity.Frequency;
import com.gmail.maxsvynarchuk.persistence.repository.FrequencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SubscriptionPlanDaoImpl implements FrequencyDao {
    private final FrequencyRepository repository;


    @Override
    public Optional<Frequency> findOne(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Frequency> findAll() {
        return repository.findAll();
    }

    @Override
    public Frequency insert(Frequency obj) {
        return repository.save(obj);
    }

    @Override
    public void update(Frequency obj) {
        repository.save(obj);
    }

    @Override
    public void delete(Frequency obj) {
        repository.delete(obj);
    }
}
