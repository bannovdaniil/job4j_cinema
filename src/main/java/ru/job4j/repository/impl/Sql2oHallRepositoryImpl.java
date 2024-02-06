package ru.job4j.repository.impl;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.model.Hall;
import ru.job4j.repository.HallRepository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oHallRepositoryImpl implements HallRepository {
    private final Sql2o sql2o;

    public Sql2oHallRepositoryImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<Hall> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT id, name, row_count, place_count, description FROM halls;");

            return query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetch(Hall.class);
        }
    }

    @Override
    public Optional<Hall> findById(int hallId) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM halls WHERE id = :hallId;");
            query.addParameter("hallId", hallId);
            Hall hall = query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetchFirst(Hall.class);

            return Optional.ofNullable(hall);
        }
    }

}