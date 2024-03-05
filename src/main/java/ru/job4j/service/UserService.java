package ru.job4j.service;

import ru.job4j.exception.UniqueConstraintException;
import ru.job4j.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> save(User user) throws UniqueConstraintException;

    Optional<User> findByEmailAndPassword(String email, String password);
}