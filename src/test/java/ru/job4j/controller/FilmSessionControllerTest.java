package ru.job4j.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.job4j.dto.FilmOutDto;
import ru.job4j.dto.FilmSessionOutDto;
import ru.job4j.exception.NotFoundException;
import ru.job4j.model.Hall;
import ru.job4j.service.FilmSessionService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FilmSessionControllerTest {
    @Mock
    private FilmSessionService mockFilmSessionService;
    @InjectMocks
    private FilmSessionController filmSessionController;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(filmSessionController).build();
    }

    @DisplayName("GetAll - получить список сеансов.")
    @Test
    void getAllThenOk() throws Exception {
        List<FilmSessionOutDto> emptyList = Collections.emptyList();

        Mockito.doReturn(emptyList).when(mockFilmSessionService).findAll();

        mvc.perform(get("/sessions"))
                .andExpect(status().isOk())
                .andExpect(view().name("sessions/list"))
                .andExpect(model().attribute("filmSessions", emptyList));

        Mockito.verify(mockFilmSessionService, Mockito.times(1)).findAll();
    }

    @DisplayName("GetById then Ok")
    @Test
    void getByIdWhenOk() throws Exception {
        int expectedId = 13;
        FilmSessionOutDto filmSession = new FilmSessionOutDto(
                expectedId,
                new FilmOutDto(),
                new Hall(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                300
        );

        Mockito.doReturn(filmSession).when(mockFilmSessionService).findById(expectedId);

        mvc.perform(get("/sessions/" + expectedId))
                .andExpect(status().isOk())
                .andExpect(view().name("sessions/order"))
                .andExpect(model().attribute("filmSession", filmSession));

        Mockito.verify(mockFilmSessionService, Mockito.times(1)).findById(expectedId);
    }

    @DisplayName("GetById then NotFound")
    @Test
    void getByIdWhenNotFound() throws Exception {
        int expectedId = 13;

        Mockito.when(mockFilmSessionService.findById(expectedId)).thenThrow(NotFoundException.class);

        mvc.perform(get("/sessions/" + expectedId))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attribute("message", "Film Session not found."));

        Mockito.verify(mockFilmSessionService, Mockito.times(1)).findById(expectedId);
    }
}
