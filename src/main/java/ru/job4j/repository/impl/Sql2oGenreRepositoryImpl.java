package ru.job4j.repository.impl;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.model.Genre;
import ru.job4j.repository.GenreRepository;

import java.util.Collection;

@Repository
public class Sql2oGenreRepositoryImpl implements GenreRepository {
    private final Sql2o sql2o;

    public Sql2oGenreRepositoryImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<Genre> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM genres");
            return query.executeAndFetch(Genre.class);
        }
    }
}