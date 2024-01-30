package ru.job4j.service;

import ru.job4j.model.Genre;

import java.util.Collection;

public interface GenreService {
    Collection<Genre> findAll();
}