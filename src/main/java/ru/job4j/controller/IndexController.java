package ru.job4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Главная страница сайта.
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String getIndex() {
        return "/index";
    }
}
