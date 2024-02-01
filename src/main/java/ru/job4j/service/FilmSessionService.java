package ru.job4j.service;

import ru.job4j.dto.FilmSessionOutDto;

import java.util.Collection;

public interface FilmSessionService {
    FilmSessionOutDto findById(int id);

    Collection<FilmSessionOutDto> findAll();
}