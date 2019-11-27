package com.gmail.maxsvynarchuk.persistence.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Common interface for all dao.
 *
 * @param <T>  represents type of domain object
 * @param <ID> represents type of identifier
 */
public interface GenericDao<T, ID extends Serializable> {
    /**
     * Retrieves object from database identified by id.
     *
     * @param id identifier of domain object.
     * @return optional, which contains retrieved object or null
     */
    Optional<T> findOne(ID id);

    /**
     * Retrieves all object data from database.
     *
     * @return List of objects which represent one row in database.
     */
    List<T> findAll();

    /**
     * Insert object to a database.
     *
     * @param obj object to insert
     * @return inserted object
     */
    T insert(T obj);

    /**
     * Update object's information in database.
     *
     * @param obj object to update
     */
    void update(T obj);

    /**
     * Delete certain object, identified by id, from database.
     *
     * @param obj user to delete.
     */
    void delete(T obj);
}
