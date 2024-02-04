package ru.job4j.service;

import ru.job4j.model.Ticket;

import java.util.List;

public interface TicketService {

    Ticket save(Ticket ticket);

    Ticket findById(int ticketId);

    List<Ticket> findAll();

    List<Ticket> findBySession(int sessionId);

    Ticket findByPlace(int sessionId, int rowNumber, int placeNumber);
}