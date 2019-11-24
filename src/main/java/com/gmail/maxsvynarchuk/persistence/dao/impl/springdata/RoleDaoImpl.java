package com.gmail.maxsvynarchuk.persistence.dao.impl.springdata;

import com.gmail.maxsvynarchuk.persistence.dao.RoleDao;
import com.gmail.maxsvynarchuk.persistence.entity.Role;
import com.gmail.maxsvynarchuk.persistence.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RoleDaoImpl implements RoleDao {
    private final RoleRepository repository;

    @Override
    public Optional<Role> findOne(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Role insert(Role obj) {
        return repository.save(obj);
    }

    @Override
    public void update(Role obj) {
        repository.save(obj);
    }

    @Override
    public void delete(Role obj) {
        repository.delete(obj);
    }

}
