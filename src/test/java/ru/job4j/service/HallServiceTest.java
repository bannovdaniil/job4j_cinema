package ru.job4j.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.repository.HallRepository;
import ru.job4j.service.impl.HallServiceImpl;

@ExtendWith(MockitoExtension.class)
class HallServiceTest {
    @Mock
    private HallRepository mockHallRepository;
    private HallService hallService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hallService = new HallServiceImpl(mockHallRepository);
    }

    @DisplayName("findAll then call once hallRepository.findAll")
    @Test
    void findAll() {
        hallService.findAll();
        Mockito.verify(mockHallRepository, Mockito.times(1)).findAll();
    }

    @DisplayName("findById then call once hallRepository.findById()")
    @Test
    void findById() {
        int expectedId = 1234;

        hallService.findById(expectedId);

        Mockito.verify(mockHallRepository, Mockito.times(1)).findById(expectedId);
    }
}