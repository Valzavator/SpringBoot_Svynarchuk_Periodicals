package com.gmail.maxsvynarchuk.persistence.dao.impl.springdata;

import com.gmail.maxsvynarchuk.persistence.dao.PeriodicalTypeDao;
import com.gmail.maxsvynarchuk.persistence.entity.PeriodicalType;
import com.gmail.maxsvynarchuk.persistence.repository.PeriodicalTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PeriodicalTypeDaoImpl implements PeriodicalTypeDao {
    private final PeriodicalTypeRepository repository;

    @Override
    public Optional<PeriodicalType> findOne(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<PeriodicalType> findAll() {
        return repository.findAll();
    }

    @Override
    public PeriodicalType insert(PeriodicalType obj) {
        return repository.save(obj);
    }

    @Override
    public void update(PeriodicalType obj) {
        repository.save(obj);
    }

    @Override
    public void delete(PeriodicalType obj) {
        repository.delete(obj);
    }

    @Override
    public boolean exist(Integer id) {
        return repository.existsById(id);
    }
}
