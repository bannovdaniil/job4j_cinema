package ru.job4j.service;

import ru.job4j.dto.FileDto;
import ru.job4j.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmService {

    Film save(Film film, FileDto fileDto);

    boolean deleteById(int id);

    boolean update(Film film, FileDto fileDto);

    Optional<Film> findById(int id);

    Collection<Film> findAll();
}