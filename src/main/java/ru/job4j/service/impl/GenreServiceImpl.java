package ru.job4j.service.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Genre;
import ru.job4j.repository.GenreRepository;
import ru.job4j.service.GenreService;

import java.util.Collection;
import java.util.Optional;

/**
 * Бизнес логика для справочника Жанры фильмов.
 */
@Service
@ThreadSafe
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository sql2oGenreRepositoryImpl) {
        this.genreRepository = sql2oGenreRepositoryImpl;
    }

    @Override
    public Collection<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(int genreId) {
        return genreRepository.findById(genreId);
    }
}