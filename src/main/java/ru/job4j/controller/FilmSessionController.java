package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.dto.FilmSessionOutDto;
import ru.job4j.service.FilmSessionService;

/**
 * Контроллер для просмотра сеансов фильмов.
 */
@Controller
@ThreadSafe
@RequestMapping("/sessions")
public class FilmSessionController {
    private final FilmSessionService filmSessionService;

    public FilmSessionController(FilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("filmSessions", filmSessionService.findAll());
        return "sessions/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        FilmSessionOutDto filmSession = filmSessionService.findById(id);
        model.addAttribute("filmSession", filmSession);
        return "sessions/order";
    }

}