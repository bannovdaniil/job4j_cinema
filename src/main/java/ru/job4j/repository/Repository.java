package ru.job4j.repository;

import java.util.Collection;
import java.util.Optional;

/**
 * ДАО работы CRUD
 */
public interface Repository<E, T> {
    E save(E value);

    boolean update(E value);

    Optional<E> findById(T id);

    Collection<E> findAll();
}
