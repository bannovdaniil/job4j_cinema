package ru.job4j.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.exception.NotFoundException;
import ru.job4j.exception.TicketPresentException;
import ru.job4j.model.FilmSession;
import ru.job4j.model.Hall;
import ru.job4j.model.Ticket;
import ru.job4j.repository.FilmSessionRepository;
import ru.job4j.repository.HallRepository;
import ru.job4j.repository.TicketRepository;
import ru.job4j.service.impl.TicketServiceImpl;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private TicketRepository mockTicketRepository;
    @Mock
    private FilmSessionRepository mockFilmSessionRepository;
    @Mock
    private HallRepository mockHallRepository;
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ticketService = new TicketServiceImpl(mockTicketRepository, mockFilmSessionRepository, mockHallRepository);
    }


    @DisplayName("save then TicketRepository.save")
    @Test
    void save() throws TicketPresentException {
        Ticket expectedTicket = new Ticket(
                10,
                23,
                10,
                3,
                13
        );
        Hall hall = new Hall("Test", 1000, 1000, "Test desc");

        Mockito.doReturn(Optional.of(new FilmSession())).when(mockFilmSessionRepository).findById(Mockito.anyInt());
        Mockito.doReturn(Optional.of(hall)).when(mockHallRepository).findById(Mockito.anyInt());

        ticketService.save(expectedTicket);

        Mockito.verify(mockTicketRepository, Mockito.times(1)).save(expectedTicket);
    }

    @DisplayName("findById then OK and call once TicketRepository.findById")
    @Test
    void findById() {
        int expectedId = 123;
        Ticket ticket = new Ticket(
                expectedId,
                23,
                10,
                3,
                13
        );
        Mockito.doReturn(Optional.of(ticket)).when(mockTicketRepository).findById(expectedId);

        ticketService.findById(expectedId);

        Mockito.verify(mockTicketRepository, Mockito.times(1)).findById(expectedId);
    }

    @DisplayName("findById then NotFoundException and call once TicketRepository.findById")
    @Test
    void findByIdThenNotFoundException() {
        int expectedId = 123;

        Mockito.doReturn(Optional.empty()).when(mockTicketRepository).findById(expectedId);
        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class, () -> ticketService.findById(expectedId)
        );

        Assertions.assertEquals("Ticket not found.", exception.getMessage());
        Mockito.verify(mockTicketRepository, Mockito.times(1)).findById(expectedId);
    }

    @DisplayName("findAll then call once TicketRepository.findAll")
    @Test
    void findAll() {
        ticketService.findAll();
        Mockito.verify(mockTicketRepository, Mockito.times(1)).findAll();
    }

    @DisplayName("findBySession then TicketRepository.findBySession")
    @Test
    void findBySession() {
        int expectedSessionId = 123;

        ticketService.findBySession(expectedSessionId);

        Mockito.verify(mockTicketRepository, Mockito.times(1)).findBySession(expectedSessionId);
    }

    @DisplayName("findByPlace then FilmSessionRepository.findById, HallRepository.findById, TicketRepository.findByPlace")
    @Test
    void findByPlace() {
        int expectedSessionId = 123;
        int expectedRowNumber = 123;
        int expectedPlaceNumber = 123;
        Hall hall = new Hall("Test", 1000, 1000, "Test desc");

        Mockito.doReturn(Optional.of(new FilmSession())).when(mockFilmSessionRepository).findById(expectedSessionId);
        Mockito.doReturn(Optional.of(hall)).when(mockHallRepository).findById(Mockito.anyInt());

        ticketService.findByPlace(expectedSessionId, expectedRowNumber, expectedPlaceNumber);

        Mockito.verify(mockFilmSessionRepository, Mockito.times(1)).findById(expectedSessionId);
        Mockito.verify(mockHallRepository, Mockito.times(1)).findById(Mockito.anyInt());
        Mockito.verify(mockTicketRepository, Mockito.times(1)).findByPlace(expectedSessionId, expectedRowNumber, expectedPlaceNumber);
    }
}