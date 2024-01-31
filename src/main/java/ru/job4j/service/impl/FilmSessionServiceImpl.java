package ru.job4j.service.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.FilmSession;
import ru.job4j.repository.FilmSessionRepository;
import ru.job4j.repository.impl.Sql2oFilmSessionRepositoryImpl;
import ru.job4j.service.FilmSessionService;

import java.util.Collection;
import java.util.Optional;

/**
 * Бизнес логика для сеансов фильмов.
 */
@Service
@ThreadSafe
public class FilmSessionServiceImpl implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;

    public FilmSessionServiceImpl(Sql2oFilmSessionRepositoryImpl sql2oFilmSessionRepository) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        return filmSessionRepository.findById(id);
    }

    @Override
    public Collection<FilmSession> findAll() {
        return filmSessionRepository.findAll();
    }
}