package ru.job4j.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.Optional;

/**
 * ДАО работы с пользователями
 */
@Repository
public class Sql2oUserRepositoryImpl implements UserRepository {
    private final Logger log = LoggerFactory.getLogger(Sql2oUserRepositoryImpl.class);
    private final Sql2o sql2o;

    public Sql2oUserRepositoryImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    INSERT INTO users(full_name, email, password)
                    VALUES (:name, :email, :password)
                    """;
            Query query = connection.createQuery(sql, true)
                    .addParameter("name", user.getFullName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Exception e) {
            log.error("User save error: {}", e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                       SELECT * FROM users
                        WHERE email = :email AND password = :password;
                    """;
            Query query = connection.createQuery(sql);
            query.addParameter("email", email);
            query.addParameter("password", password);
            User user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }
}
