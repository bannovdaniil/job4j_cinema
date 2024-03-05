package ru.job4j.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.exception.UniqueConstraintException;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;
import ru.job4j.service.impl.UserServiceImpl;

import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository mockUserRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        userService = new UserServiceImpl(mockUserRepository);
    }

    @DisplayName("Save then call one UserRepository save")
    @Test
    void save() throws UniqueConstraintException {
        User user = new User(
                "test full name",
                "e@mail.ru",
                "test password"
        );

        userService.save(user);

        Mockito.verify(mockUserRepository, Mockito.times(1)).save(user);
    }

    @DisplayName("findByEmailAndPassword then once call UserRepository findByEmailAndPassword")
    @Test
    void findByEmailAndPassword() {
        String expectedEmail = "login";
        String expectedPassword = "password";

        userService.findByEmailAndPassword(expectedEmail, expectedPassword);

        Mockito.verify(mockUserRepository, Mockito.times(1)).findByEmailAndPassword(expectedEmail, expectedPassword);
    }
}