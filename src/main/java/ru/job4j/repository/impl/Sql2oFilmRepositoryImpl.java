package ru.job4j.repository.impl;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.model.Film;
import ru.job4j.repository.FilmRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * ДАО работы с фильмами
 */
@Repository
public class Sql2oFilmRepositoryImpl implements FilmRepository {
    private final Sql2o sql2o;

    public Sql2oFilmRepositoryImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Film save(Film film) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    INSERT INTO films(name, description, year, genre_id, minimal_age, duration_in_minutes, file_id)
                    VALUES (:name, :description, :year, :genreId, :minimalAge, :durationInMinutes, :fileId)
                    """;
            Query query = connection.createQuery(sql, true)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("year", film.getYear())
                    .addParameter("genreId", film.getGenreId())
                    .addParameter("minimalAge", film.getMinimalAge())
                    .addParameter("durationInMinutes", film.getDurationInMinutes())
                    .addParameter("fileId", film.getFileId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            film.setId(generatedId);
            return film;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean result;
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("DELETE FROM films WHERE id = :id");
            query.addParameter("id", id);
            query.executeUpdate();
            result = connection.getResult() != 0;
        }
        return result;
    }

    @Override
    public boolean update(Film film) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    UPDATE films
                    SET name = :name, description = :description,
                        year = :year, genre_id = :genreId,
                        minimal_age = :minimalAge, duration_in_minutes = :durationInMinutes,
                        file_id = :fileId
                    WHERE id = :id
                    """;
            Query query = connection.createQuery(sql)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("year", film.getYear())
                    .addParameter("genreId", film.getGenreId())
                    .addParameter("minimalAge", film.getMinimalAge())
                    .addParameter("durationInMinutes", film.getDurationInMinutes())
                    .addParameter("fileId", film.getFileId());
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<Film> findById(Integer id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM films WHERE id = :id");
            query.addParameter("id", id);
            Film film = query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }

    @Override
    public Collection<Film> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM films");
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }
}
