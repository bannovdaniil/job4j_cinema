package ru.job4j.repository.impl;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.model.FilmSession;
import ru.job4j.repository.FilmSessionRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * ДАО работы с сеансами фильмов
 */
@Repository
public class Sql2oFilmSessionRepositoryImpl implements FilmSessionRepository {
    private final Sql2o sql2o;

    public Sql2oFilmSessionRepositoryImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public FilmSession save(FilmSession filmSession) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    INSERT INTO film_sessions(film_id, hall_id, start_time, end_time, price)
                    VALUES (:filmId, :hallId, :startTime, :endTime, :price)
                    """;
            Query query = connection.createQuery(sql, true)
                    .addParameter("filmId", filmSession.getFilmId())
                    .addParameter("hallId", filmSession.getHallId())
                    .addParameter("startTime", filmSession.getStartTime())
                    .addParameter("endTime", filmSession.getEndTime())
                    .addParameter("price", filmSession.getPrice());
            int generatedId = query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeUpdate().getKey(Integer.class);
            filmSession.setId(generatedId);
            return filmSession;
        }
    }

    @Override
    public boolean update(FilmSession filmSession) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    UPDATE film_sessions
                    SET film_id = :filmId, hall_id = :hallId,
                        start_time = :startTime, end_time = :endTime,
                        price = :price
                    WHERE id = :id
                    """;
            Query query = connection.createQuery(sql)
                    .addParameter("filmId", filmSession.getFilmId())
                    .addParameter("hallId", filmSession.getHallId())
                    .addParameter("startTime", filmSession.getStartTime())
                    .addParameter("endTime", filmSession.getEndTime())
                    .addParameter("price", filmSession.getPrice())
                    .addParameter("id", filmSession.getId());
            int affectedRows = query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<FilmSession> findById(Integer id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id");
            query.addParameter("id", id);
            FilmSession filmSession = query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetchFirst(FilmSession.class);
            return Optional.ofNullable(filmSession);
        }
    }

    @Override
    public Collection<FilmSession> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM film_sessions ORDER BY hall_id, start_time;");
            return query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetch(FilmSession.class);
        }
    }
}
