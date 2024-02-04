package ru.job4j.service;

import ru.job4j.model.Genre;

import java.util.Collection;
import java.util.Optional;

public interface GenreService {
    Collection<Genre> findAll();

    Optional<Genre> findById(int genreId);
}