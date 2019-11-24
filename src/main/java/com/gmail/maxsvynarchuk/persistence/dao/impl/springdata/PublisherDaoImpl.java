package com.gmail.maxsvynarchuk.persistence.dao.impl.springdata;

import com.gmail.maxsvynarchuk.persistence.dao.PublisherDao;
import com.gmail.maxsvynarchuk.persistence.entity.Publisher;
import com.gmail.maxsvynarchuk.persistence.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PublisherDaoImpl implements PublisherDao {
    private final PublisherRepository repository;

    @Override
    public Optional<Publisher> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Publisher> findAll() {
        return repository.findAll();
    }

    @Override
    public Publisher insert(Publisher obj) {
        return repository.save(obj);
    }

    @Override
    public void update(Publisher obj) {
        repository.save(obj);
    }

    @Override
    public void delete(Publisher obj) {
        repository.delete(obj);
    }
}
