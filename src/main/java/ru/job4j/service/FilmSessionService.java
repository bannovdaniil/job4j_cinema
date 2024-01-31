package ru.job4j.service;

import ru.job4j.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

public interface FilmSessionService {
    Optional<FilmSession> findById(int id);

    Collection<FilmSession> findAll();
}