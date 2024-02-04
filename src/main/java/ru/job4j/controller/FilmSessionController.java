package ru.job4j.controller;

import jakarta.servlet.http.HttpSession;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.dto.FilmSessionOutDto;
import ru.job4j.exception.NotFoundException;
import ru.job4j.service.FilmService;
import ru.job4j.service.FilmSessionService;

/**
 * Контроллер для просмотра фильмов.
 */
@Controller
@ThreadSafe
@RequestMapping("/sessions")
public class FilmSessionController {
    private static final String NOT_FOUND_SESSION_MESSAGE = "Film Session not found.";
    private final FilmSessionService filmSessionService;
    private final FilmService filmService;

    public FilmSessionController(FilmSessionService filmSessionService, FilmService filmService) {
        this.filmSessionService = filmSessionService;
        this.filmService = filmService;
    }

    private static String sendNotFoundError(Model model, String message) {
        model.addAttribute("message", message);
        return "errors/404";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("filmSessions", filmSessionService.findAll());
        model.addAttribute("films", filmService.findAll());
        return "sessions/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, HttpSession session) {
        try {
            FilmSessionOutDto filmSession = filmSessionService.findById(id);
            model.addAttribute("filmSession", filmSession);
        } catch (NotFoundException e) {
            return sendNotFoundError(model, NOT_FOUND_SESSION_MESSAGE);
        }
        return "sessions/order";
    }

}