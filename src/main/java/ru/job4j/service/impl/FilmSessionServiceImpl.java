package ru.job4j.service.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dto.FilmSessionOutDto;
import ru.job4j.dto.PlaceDto;
import ru.job4j.exception.NotFoundException;
import ru.job4j.mapper.FilmSessionMapper;
import ru.job4j.model.FilmSession;
import ru.job4j.model.Hall;
import ru.job4j.repository.FilmSessionRepository;
import ru.job4j.repository.HallRepository;
import ru.job4j.repository.TicketRepository;
import ru.job4j.repository.impl.Sql2oFilmSessionRepositoryImpl;
import ru.job4j.repository.impl.Sql2oHallRepositoryImpl;
import ru.job4j.repository.impl.Sql2oTicketRepositoryImpl;
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
    private final TicketRepository ticketRepository;
    private final FilmSessionMapper filmSessionMapper;

    public FilmSessionServiceImpl(Sql2oFilmSessionRepositoryImpl sql2oFilmSessionRepository,
                                  FilmService filmService,
                                  FilmSessionMapper filmSessionMapper,
                                  Sql2oHallRepositoryImpl sql2oHallRepository, Sql2oTicketRepositoryImpl sql2oTicketRepository) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.filmService = filmService;
        this.filmSessionMapper = filmSessionMapper;
        this.hallRepository = sql2oHallRepository;
        this.ticketRepository = sql2oTicketRepository;
    }

    /**
     * С добавлением списка свободных мест.
     *
     * @param sessionId - сеанс
     * @return - сущность
     */
    @Override
    public FilmSessionOutDto findById(int sessionId) {
        FilmSession filmSession = filmSessionRepository.findById(sessionId).orElseThrow(
                () -> new NotFoundException("Сеанс не найден.")
        );
        Hall hall = hallRepository.findById(filmSession.getHallId()).orElseThrow(
                () -> new NotFoundException("Hall for session not present."));

        List<List<PlaceDto>> placeList = getPlaceLists(sessionId, hall);

        return filmSessionMapper.map(filmSession, filmService.findById(filmSession.getFilmId()), hall, placeList);
    }

    /**
     * Формирование списка свободных мест.
     *
     * @param sessionId - сессия
     * @param hall      - зал
     * @return - список мест в зале
     */
    private List<List<PlaceDto>> getPlaceLists(int sessionId, Hall hall) {
        List<List<PlaceDto>> rowList = new ArrayList<>(hall.getRowCount());
        for (int row = 1; row <= hall.getRowCount(); row++) {
            List<PlaceDto> placeList = new ArrayList<>(hall.getPlaceCount());
            for (int place = 1; place <= hall.getPlaceCount(); place++) {
                placeList.add(
                        new PlaceDto(ticketRepository.findByPlace(sessionId, row, place).isEmpty(), row, place)
                );
            }
            rowList.add(placeList);
        }
        return rowList;
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