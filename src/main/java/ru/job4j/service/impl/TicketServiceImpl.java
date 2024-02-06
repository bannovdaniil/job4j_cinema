package ru.job4j.service.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.exception.NotFoundException;
import ru.job4j.model.FilmSession;
import ru.job4j.model.Hall;
import ru.job4j.model.Ticket;
import ru.job4j.repository.FilmSessionRepository;
import ru.job4j.repository.HallRepository;
import ru.job4j.repository.TicketRepository;
import ru.job4j.service.TicketService;

import java.util.List;
import java.util.Optional;

/**
 * Бизнес логика для Билетов.
 */
@Service
@ThreadSafe
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final FilmSessionRepository filmSessionRepository;
    private final HallRepository hallRepository;

    public TicketServiceImpl(TicketRepository sql2oTicketRepository,
                             FilmSessionRepository sql2oFilmSessionRepository,
                             HallRepository sql2oHallRepository) {
        this.ticketRepository = sql2oTicketRepository;
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.hallRepository = sql2oHallRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket findById(int ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(
                () -> new NotFoundException("Ticket not found.")
        );
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> findBySession(int sessionId) {
        return ticketRepository.findBySession(sessionId);
    }

    @Override
    public Optional<Ticket> findByPlace(int sessionId, int rowNumber, int placeNumber) {
        if (rowNumber < 1 || placeNumber < 1) {
            throw new IllegalArgumentException("Таких мест не существует.");
        }
        FilmSession filmSession = filmSessionRepository.findById(sessionId).orElseThrow(
                () -> new NotFoundException("Session for ticket not found.")
        );
        Hall hall = hallRepository.findById(filmSession.getHallId()).orElseThrow(
                () -> new NotFoundException("Hall for session in ticket not found.")
        );
        if (rowNumber > hall.getRowCount() || placeNumber > hall.getPlaceCount()) {
            throw new IllegalArgumentException("Таких мест не существует.");
        }

        return ticketRepository.findByPlace(sessionId, rowNumber, placeNumber);
    }
}