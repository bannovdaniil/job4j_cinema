package ru.job4j.repository.impl;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.model.Ticket;
import ru.job4j.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

/**
 * ДАО работы с билетами
 */
@Repository
public class Sql2oTicketRepositoryImpl implements TicketRepository {
    private final Sql2o sql2o;

    public Sql2oTicketRepositoryImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Ticket save(Ticket ticket) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    INSERT INTO tickets(session_id, row_number, place_number, user_id)
                    VALUES (:sessionId, :rowNumber, :placeNumber, :userId)
                    """;
            Query query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return ticket;
        }
    }

    @Override
    public Optional<Ticket> findById(int ticketId) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM tickets WHERE id = :ticketId");
            query.addParameter("ticketId", ticketId);
            Ticket ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    @Override
    public List<Ticket> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM tickets");
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }

    /**
     * Найти билет по сеансу
     *
     * @param sessionId - сеанс
     * @return - билет
     */
    @Override
    public List<Ticket> findBySession(int sessionId) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("""
                    SELECT * FROM tickets
                    WHERE session_id = :sessionId
                    """);
            query.addParameter("sessionId", sessionId);
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }

    /**
     * Найти билет по сеансу, ряду, месту.
     *
     * @param sessionId   - сеанс
     * @param rowNumber   - ряд
     * @param placeNumber - место
     * @return - билет
     */
    @Override
    public Optional<Ticket> findByPlace(int sessionId, int rowNumber, int placeNumber) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("""
                    SELECT * FROM tickets
                     WHERE session_id = :sessionId AND row_number = :rowNumber AND place_number = :placeNumber
                    """);
            query.addParameter("sessionId", sessionId)
                    .addParameter("rowNumber", rowNumber)
                    .addParameter("placeNumber", placeNumber);
            Ticket ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }
}
