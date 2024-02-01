package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.dto.FilmOutDto;
import ru.job4j.exception.NotFoundException;
import ru.job4j.service.FilmService;

/**
 * Контроллер для просмотра фильмов.
 */
@Controller
@ThreadSafe
@RequestMapping("/films")
public class FilmController {
    private static final String NOT_FOUND_FILM_MESSAGE = "Film ID not found.";
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    private static String sendNotFoundError(Model model, String message) {
        model.addAttribute("message", message);
        return "errors/404";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "films/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        try {
            FilmOutDto film = filmService.findById(id);
            model.addAttribute("film", film);
        } catch (NotFoundException e) {
            return sendNotFoundError(model, NOT_FOUND_FILM_MESSAGE);
        }
        return "films/one";
    }
}