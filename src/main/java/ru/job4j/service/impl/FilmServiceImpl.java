package ru.job4j.service.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dto.FileDto;
import ru.job4j.dto.FilmOutDto;
import ru.job4j.exception.NotFoundException;
import ru.job4j.mapper.FilmMapper;
import ru.job4j.model.File;
import ru.job4j.model.Film;
import ru.job4j.model.Genre;
import ru.job4j.repository.FilmRepository;
import ru.job4j.repository.GenreRepository;
import ru.job4j.service.FileService;
import ru.job4j.service.FilmService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Бизнес логика для Фильмов.
 */
@Service
@ThreadSafe
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final FileService fileService;
    private final FilmMapper filmMapper;

    public FilmServiceImpl(FilmRepository sql2OFilmRepositoryImpl, GenreRepository genreRepository, FileService fileService, FilmMapper filmMapper) {
        this.filmRepository = sql2OFilmRepositoryImpl;
        this.genreRepository = genreRepository;
        this.fileService = fileService;
        this.filmMapper = filmMapper;
    }

    @Override
    public Film save(Film film, FileDto fileDto) {
        saveNewFile(film, fileDto);
        return filmRepository.save(film);
    }

    private void saveNewFile(Film film, FileDto fileDto) {
        File file = fileService.save(fileDto);
        film.setFileId(file.getId());
    }

    /**
     * Если передан новый не пустой файл, то старый удаляем, а новый сохраняем
     */
    @Override
    public boolean update(Film film, FileDto fileDto) {
        var isNewFileEmpty = fileDto.getContent().length == 0;
        if (isNewFileEmpty) {
            return filmRepository.update(film);
        }
        var oldFileId = film.getFileId();
        saveNewFile(film, fileDto);
        var isUpdated = filmRepository.update(film);
        fileService.deleteById(oldFileId);
        return isUpdated;
    }

    @Override
    public FilmOutDto findById(int id) {
        Film film = filmRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Film not found")
        );
        return getFilmOutDto(film);
    }


    @Override
    public List<FilmOutDto> findAll() {
        Collection<Film> filmList = filmRepository.findAll();
        List<FilmOutDto> filmOutDtoList = new ArrayList<>();

        for (Film film : filmList) {
            filmOutDtoList.add(getFilmOutDto(film));
        }

        return filmOutDtoList;
    }

    private FilmOutDto getFilmOutDto(Film film) {
        Genre genre = genreRepository.findById(film.getGenreId()).orElseThrow(
                () -> new NotFoundException("Film not applied to genre.")
        );
        String duration = String.format("%02d:%02d:00", film.getDurationInMinutes() / 60, film.getDurationInMinutes() % 60);

        return filmMapper.map(film, genre, duration);
    }

}