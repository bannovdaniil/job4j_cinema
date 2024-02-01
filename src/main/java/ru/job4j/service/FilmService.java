package ru.job4j.service;

import ru.job4j.dto.FileDto;
import ru.job4j.dto.FilmOutDto;
import ru.job4j.model.Film;

import java.util.List;

public interface FilmService {

    Film save(Film film, FileDto fileDto);

    boolean deleteById(int id);

    boolean update(Film film, FileDto fileDto);

    FilmOutDto findById(int id);

    List<FilmOutDto> findAll();
}