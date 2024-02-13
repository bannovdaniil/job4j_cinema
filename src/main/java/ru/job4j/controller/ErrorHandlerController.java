package ru.job4j.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import ru.job4j.exception.FileException;
import ru.job4j.exception.NotFoundException;
import ru.job4j.exception.TicketPresentException;
import ru.job4j.model.FilmSession;

@RestControllerAdvice(assignableTypes = {
        FileController.class,
        FilmController.class,
        FilmSession.class,
        IndexController.class,
        TicketController.class,
        UserController.class}
)
public class ErrorHandlerController {
    private final Logger logger = LoggerFactory.getLogger(ErrorHandlerController.class);

    @ExceptionHandler({
            FileException.class
    })
    public ModelAndView handleFileError(final Exception e) {
        logger.error(e.getMessage());
        ModelAndView modelAndView = new ModelAndView("redirect:/img/noimg.png");
        modelAndView.setStatus(HttpStatus.TEMPORARY_REDIRECT);
        return modelAndView;
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
    })
    public ModelAndView handleBadRequest(final Exception e) {
        logger.error(e.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/error");
        modelAndView.addObject("message", "Ошибка валидации данных.");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }

    @ExceptionHandler({NotFoundException.class})
    public ModelAndView handleNotFound(final Exception e) {
        logger.error(e.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/error");
        modelAndView.addObject("message", "Сущность не найдена.");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler({TicketPresentException.class})
    public ModelAndView ticketPresent(final TicketPresentException e) {
        logger.error(e.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tickets/error");
        modelAndView.addObject("ticket", e.getTicket());
        modelAndView.setStatus(HttpStatus.CONFLICT);
        return modelAndView;
    }


    @ExceptionHandler({Exception.class})
    public ModelAndView handleAllError(final Throwable e) {
        logger.error(e.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/error");
        modelAndView.addObject("message", "Непредвиденная ошибка, сообщите администратору.");
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }
}
