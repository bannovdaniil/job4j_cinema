package ru.job4j.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.repository.GenreRepository;
import ru.job4j.service.impl.GenreServiceImpl;

import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {
    @Mock
    private GenreRepository mockGenreRepository;
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        genreService = new GenreServiceImpl(mockGenreRepository);
    }

    @DisplayName("findAll then call once GenreRepository.findAll")
    @Test
    void findAll() {
        genreService.findAll();

        Mockito.verify(mockGenreRepository, Mockito.times(1)).findAll();
    }

    @DisplayName("findById then call once GenreRepository.findById")
    @Test
    void findById() {
        int expectedId = 123;

        genreService.findById(expectedId);

        Mockito.verify(mockGenreRepository).findById(expectedId);
    }
}