package ru.job4j.repository;

import ru.job4j.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    Ticket save(Ticket ticket);

    Optional<Ticket> findById(int ticketId);

    List<Ticket> findAll();

    List<Ticket> findBySession(int sessionId);

    Optional<Ticket> findByPlace(int sessionId, int rowNumber, int placeNumber);
}
