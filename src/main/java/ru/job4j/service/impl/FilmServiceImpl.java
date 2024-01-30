package ru.job4j.service.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dto.FileDto;
import ru.job4j.model.File;
import ru.job4j.model.Film;
import ru.job4j.repository.FilmRepository;
import ru.job4j.service.FileService;
import ru.job4j.service.FilmService;

import java.util.Collection;
import java.util.Optional;

/**
 * Бизнес логика для Фильмов.
 */
@Service
@ThreadSafe
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FileService fileService;

    public FilmServiceImpl(FilmRepository sql2OFilmRepositoryImpl, FileService fileService) {
        this.filmRepository = sql2OFilmRepositoryImpl;
        this.fileService = fileService;
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

    @Override
    public boolean deleteById(int id) {
        var film = findById(id);
        if (film.isPresent()) {
            filmRepository.deleteById(id);
            fileService.deleteById(film.get().getFileId());
        }
        return filmRepository.deleteById(id);
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
    public Optional<Film> findById(int id) {
        return filmRepository.findById(id);
    }

    @Override
    public Collection<Film> findAll() {
        return filmRepository.findAll();
    }
}