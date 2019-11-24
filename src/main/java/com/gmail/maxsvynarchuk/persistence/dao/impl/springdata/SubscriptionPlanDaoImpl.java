package com.gmail.maxsvynarchuk.persistence.dao.impl.springdata;

import com.gmail.maxsvynarchuk.persistence.dao.SubscriptionPlanDao;
import com.gmail.maxsvynarchuk.persistence.entity.SubscriptionPlan;
import com.gmail.maxsvynarchuk.persistence.repository.SubscriptionPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SubscriptionPlanDaoImpl implements SubscriptionPlanDao {
    private final SubscriptionPlanRepository repository;

    @Override
    public Optional<SubscriptionPlan> findOne(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<SubscriptionPlan> findAll() {
        return repository.findAll();
    }

    @Override
    public SubscriptionPlan insert(SubscriptionPlan obj) {
        return repository.save(obj);
    }

    @Override
    public void update(SubscriptionPlan obj) {
        repository.save(obj);
    }

    @Override
    public void delete(SubscriptionPlan obj) {
        repository.delete(obj);
    }
}
