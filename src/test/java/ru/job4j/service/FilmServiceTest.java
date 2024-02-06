package ru.job4j.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.dto.FileDto;
import ru.job4j.exception.NotFoundException;
import ru.job4j.mapper.FilmMapper;
import ru.job4j.model.File;
import ru.job4j.model.Film;
import ru.job4j.model.Genre;
import ru.job4j.repository.FilmRepository;
import ru.job4j.repository.GenreRepository;
import ru.job4j.service.impl.FilmServiceImpl;

import java.util.Optional;

import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {
    @Mock
    private FilmRepository mockFilmRepository;
    @Mock
    private GenreRepository mockGenreRepository;
    @Mock
    private FileService mockFileService;
    private FilmMapper filmMapper;
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        filmMapper = new FilmMapper();
        filmService = new FilmServiceImpl(mockFilmRepository, mockGenreRepository, mockFileService, filmMapper);
    }

    @DisplayName("Save then call FileService.save , FilmRepository.save")
    @Test
    void save() {
        FileDto fileDto = new FileDto("test.txt", "test".getBytes());
        File file = new File("tt.txt", "files");
        file.setId(33);
        Film film = new Film(
                "name", "descriptions", 2023, 10, 17, 120, 33
        );
        Mockito.doReturn(file).when(mockFileService).save(fileDto);

        filmService.save(film, fileDto);

        Mockito.verify(mockFileService, Mockito.times(1)).save(fileDto);
        Mockito.verify(mockFilmRepository, Mockito.times(1)).save(film);
    }

    @DisplayName("Save then call FileService.save, delete(oldFileId) , FilmRepository.update")
    @Test
    void update() {
        int expectedDeleteFileId = 133;

        FileDto fileDto = new FileDto("test.txt", "test".getBytes());
        File file = new File("tt.txt", "files");
        file.setId(34);

        Film film = new Film(
                "name", "descriptions", 2023, 10, 17, 120, expectedDeleteFileId
        );
        Mockito.doReturn(file).when(mockFileService).save(fileDto);

        filmService.update(film, fileDto);

        Mockito.verify(mockFileService, Mockito.times(1)).deleteById(expectedDeleteFileId);
        Mockito.verify(mockFileService, Mockito.times(1)).save(fileDto);
        Mockito.verify(mockFilmRepository, Mockito.times(1)).update(film);
    }

    @DisplayName("findById then OK and call FilmRepository.findById, GenreRepository.findById")
    @Test
    void findById() {
        int expectedId = 134;
        Film film = new Film(
                "name", "descriptions", 2023, 1, 17, 120, 1
        );
        film.setId(expectedId);
        Mockito.doReturn(Optional.of(film)).when(mockFilmRepository).findById(expectedId);
        Mockito.doReturn(Optional.of(new Genre())).when(mockGenreRepository).findById(Mockito.anyInt());

        filmService.findById(expectedId);

        Mockito.verify(mockGenreRepository, Mockito.times(1)).findById(Mockito.anyInt());
        Mockito.verify(mockFilmRepository, Mockito.times(1)).findById(expectedId);
    }

    @DisplayName("findById then NotFoundException && call FilmRepository.findById")
    @Test
    void findByIdThenNotFoundException() {
        int expectedId = 134;

        Mockito.doReturn(Optional.empty()).when(mockFilmRepository).findById(expectedId);

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class, () -> filmService.findById(expectedId)
        );

        Assertions.assertEquals("Film not found", exception.getMessage());
        Mockito.verify(mockFilmRepository, Mockito.times(1)).findById(expectedId);
    }

    @DisplayName("findAll then call once FilmRepository.findAll")
    @Test
    void findAll() {
        filmService.findAll();

        Mockito.verify(mockFilmRepository, Mockito.times(1)).findAll();
    }
}