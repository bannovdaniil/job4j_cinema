package ru.job4j.exception;

import ru.job4j.model.Ticket;

public class TicketPresentException extends RuntimeException {
    private final transient Ticket ticket;

    public TicketPresentException(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
