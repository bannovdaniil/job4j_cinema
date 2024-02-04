package ru.job4j.repository;


import ru.job4j.model.Genre;

import java.util.Collection;
import java.util.Optional;

/**
 * Репозиторий для работы с жанрами фильмов.
 */
public interface GenreRepository {
    Collection<Genre> findAll();

    Optional<Genre> findById(int genreId);
}
