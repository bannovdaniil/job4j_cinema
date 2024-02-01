package ru.job4j.repository;


import ru.job4j.model.Genre;

import java.util.Collection;
import java.util.Optional;

public interface GenreRepository {
    Collection<Genre> findAll();

    Optional<Genre> findById(int genreId);
}
