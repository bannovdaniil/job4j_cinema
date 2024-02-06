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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.job4j.exception.NotFoundException;
import ru.job4j.service.FilmService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FilmControllerTest {
    @Mock
    private FilmService filmService;
    @InjectMocks
    private FilmController filmController;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(filmController).build();
    }

    @DisplayName("GetById then - ok")
    @Test
    void whenGetFilmThenOk() throws Exception {
        int expectedId = 12;

        mvc.perform(get("/films/" + expectedId))
                .andExpect(status().isOk())
                .andExpect(view().name("films/one"));

        Mockito.verify(filmService, Mockito.times(1)).findById(expectedId);
    }

    @DisplayName("GetById when - NotFound")
    @Test
    void whenGetFilmThenNotFound() throws Exception {
        int expectedId = 12;

        Mockito.when(filmService.findById(expectedId)).thenThrow(NotFoundException.class);

        mvc.perform(get("/films/" + expectedId))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"));

        Mockito.verify(filmService, Mockito.times(1)).findById(expectedId);
    }

    @DisplayName("GetAll then - ok")
    @Test
    void whenGetAllThenOk() throws Exception {

        mvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(view().name("films/list"));

        Mockito.verify(filmService, Mockito.times(1)).findAll();
    }


}