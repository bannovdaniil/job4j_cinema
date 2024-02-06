package ru.job4j.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.mapper.FilmSessionMapper;
import ru.job4j.model.FilmSession;
import ru.job4j.model.Hall;
import ru.job4j.repository.FilmSessionRepository;
import ru.job4j.repository.HallRepository;
import ru.job4j.repository.TicketRepository;
import ru.job4j.service.impl.FilmSessionServiceImpl;

import java.util.Optional;

import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
class FilmSessionServiceTest {
    @Mock
    private FilmSessionRepository mockFilmSessionRepository;
    @Mock
    private HallRepository mockHallRepository;
    @Mock
    private FilmService mockFilmService;
    @Mock
    private TicketRepository mockTicketRepository;
    private FilmSessionMapper filmSessionMapper;
    private FilmSessionService filmSessionService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        filmSessionMapper = new FilmSessionMapper();
        filmSessionService = new FilmSessionServiceImpl(mockFilmSessionRepository, mockFilmService, filmSessionMapper, mockHallRepository, mockTicketRepository);
    }

    @DisplayName("findById then call once FilmSessionRepository.findById")
    @Test
    void findById() {
        int expectedId = 123;

        Mockito.doReturn(Optional.of(new FilmSession())).when(mockFilmSessionRepository).findById(expectedId);
        Mockito.doReturn(Optional.of(new Hall())).when(mockHallRepository).findById(Mockito.anyInt());

        filmSessionService.findById(expectedId);

        Mockito.verify(mockFilmSessionRepository, Mockito.times(1)).findById(expectedId);
    }

    @DisplayName("findAll then call once FilmSessionRepository.findAll")
    @Test
    void findAll() {
        filmSessionService.findAll();

        Mockito.verify(mockFilmSessionRepository, Mockito.times(1)).findAll();
    }
}