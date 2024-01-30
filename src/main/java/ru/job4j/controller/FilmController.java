package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.model.Film;
import ru.job4j.model.Genre;
import ru.job4j.service.FilmService;
import ru.job4j.service.GenreService;

import java.util.Collection;
import java.util.Optional;

/**
 * Контроллер для просмотра фильмов.
 */
@Controller
@ThreadSafe
@RequestMapping("/films")
public class FilmController {
    private static final String NOT_FOUND_FILM_MESSAGE = "Film ID not found.";
    private final FilmService filmService;
    private final GenreService genreService;

    public FilmController(FilmService filmService, GenreService genreService) {
        this.filmService = filmService;
        this.genreService = genreService;
    }

    private static String sendNotFoundError(Model model, String message) {
        model.addAttribute("message", message);
        return "errors/404";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "films/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        Optional<Film> filmOptional = filmService.findById(id);
        if (filmOptional.isEmpty()) {
            return sendNotFoundError(model, NOT_FOUND_FILM_MESSAGE);
        }
        model.addAttribute("film", filmOptional.get());
        model.addAttribute("genres", genreService.findAll());
        return "films/one";
    }
}