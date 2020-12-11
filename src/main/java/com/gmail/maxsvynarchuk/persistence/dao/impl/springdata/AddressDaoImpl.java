package com.gmail.maxsvynarchuk.persistence.dao.impl.springdata;

import com.gmail.maxsvynarchuk.persistence.dao.AddressDao;
import com.gmail.maxsvynarchuk.persistence.entity.Address;
import com.gmail.maxsvynarchuk.persistence.entity.Role;
import com.gmail.maxsvynarchuk.persistence.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AddressDaoImpl implements AddressDao {
    private final AddressRepository repository;

    @Override
    public Optional<Address> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Address> findAll() {
        return repository.findAll();
    }

    @Override
    public Address insert(Address obj) {
        return repository.save(obj);
    }

    @Override
    public void update(Address obj) {
        repository.save(obj);
    }

    @Override
    public void delete(Address obj) {
        repository.delete(obj);
    }

    @Override
    public boolean exist(Long id) {
        return repository.existsById(id);
    }
}
