package ru.job4j.repository;


import ru.job4j.model.Genre;

import java.util.Collection;

public interface GenreRepository {
    Collection<Genre> findAll();
}
