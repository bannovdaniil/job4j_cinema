package ru.job4j.repository;

import ru.job4j.model.Hall;

import java.util.Collection;
import java.util.Optional;

/**
 * Репозиторий для работы с залами
 */
public interface HallRepository {
    Collection<Hall> findAll();

    Optional<Hall> findById(int hallId);
}
