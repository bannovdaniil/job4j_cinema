package ru.job4j.service.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.exception.NotFoundException;
import ru.job4j.model.Ticket;
import ru.job4j.repository.TicketRepository;
import ru.job4j.repository.impl.Sql2oTicketRepositoryImpl;
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

    public TicketServiceImpl(Sql2oTicketRepositoryImpl sql2oTicketRepository) {
        this.ticketRepository = sql2oTicketRepository;
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
        return ticketRepository.findByPlace(sessionId, rowNumber, placeNumber);
    }
}