package ru.job4j.service.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.exception.UniqueConstraintException;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;
import ru.job4j.service.UserService;

import java.util.Optional;

/**
 * Бизнес логика для Пользователей.
 */
@Service
@ThreadSafe
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository sql2oUserRepositoryImpl) {
        this.userRepository = sql2oUserRepositoryImpl;
    }

    @Override
    public Optional<User> save(User user) throws UniqueConstraintException {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}