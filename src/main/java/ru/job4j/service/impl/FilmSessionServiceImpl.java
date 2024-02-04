package ru.job4j.service.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dto.FilmSessionOutDto;
import ru.job4j.exception.NotFoundException;
import ru.job4j.mapper.FilmSessionMapper;
import ru.job4j.model.FilmSession;
import ru.job4j.model.Hall;
import ru.job4j.repository.FilmSessionRepository;
import ru.job4j.repository.HallRepository;
import ru.job4j.repository.impl.Sql2oFilmSessionRepositoryImpl;
import ru.job4j.repository.impl.Sql2oHallRepositoryImpl;
import ru.job4j.service.FilmService;
import ru.job4j.service.FilmSessionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Бизнес логика для сеансов фильмов.
 */
@Service
@ThreadSafe
public class FilmSessionServiceImpl implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final HallRepository hallRepository;
    private final FilmService filmService;
    private final FilmSessionMapper filmSessionMapper;

    public FilmSessionServiceImpl(Sql2oFilmSessionRepositoryImpl sql2oFilmSessionRepository,
                                  FilmService filmService,
                                  FilmSessionMapper filmSessionMapper,
                                  Sql2oHallRepositoryImpl sql2oHallRepository) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.filmService = filmService;
        this.filmSessionMapper = filmSessionMapper;
        this.hallRepository = sql2oHallRepository;
    }

    @Override
    public FilmSessionOutDto findById(int id) {
        FilmSession filmSession = filmSessionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Сеанс не найден.")
        );

        return filmSessionMapper.map(filmSession, filmService.findById(filmSession.getFilmId()), new Hall());
    }

    @Override
    public List<FilmSessionOutDto> findAll() {
        Collection<FilmSession> filmSessionsList = filmSessionRepository.findAll();
        List<FilmSessionOutDto> filmSessionOutDtoList = new ArrayList<>();

        for (FilmSession filmSession : filmSessionsList) {
            filmSessionOutDtoList.add(
                    filmSessionMapper.map(filmSession, filmService.findById(filmSession.getFilmId()),
                            hallRepository.findById(filmSession.getHallId()).orElseThrow(
                                    () -> new NotFoundException("Hall for session not present.")
                            )
                    )
            );
        }

        return filmSessionOutDtoList;
    }
}