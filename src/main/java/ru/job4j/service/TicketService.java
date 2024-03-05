package ru.job4j.service;

import ru.job4j.exception.TicketPresentException;
import ru.job4j.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    Ticket save(Ticket ticket) throws TicketPresentException;

    Ticket findById(int ticketId);

    List<Ticket> findAll();

    List<Ticket> findBySession(int sessionId);

    Optional<Ticket> findByPlace(int sessionId, int rowNumber, int placeNumber);
}