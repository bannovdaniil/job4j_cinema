package ru.job4j.controller;

import jakarta.servlet.http.HttpSession;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.model.Ticket;
import ru.job4j.service.TicketService;

import java.util.Optional;

/**
 * Контроллер работы с Пользователями
 */
@Controller
@ThreadSafe
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/order")
    public String loginUser(@ModelAttribute Ticket ticket, HttpSession session,
                            @RequestParam(value = "placeBox", defaultValue = "x") String placeBox,
                            BindingResult bindingResult, Model model) {
        if (!"x".equals(placeBox) && placeBox.matches("^\\d+-\\d+$")) {
            String[] places = placeBox.split("-");
            ticket.setRowNumber(Integer.parseInt(places[0]));
            ticket.setPlaceNumber(Integer.parseInt(places[1]));
        }

        Optional<Ticket> requiredTicket = ticketService.findByPlace(ticket.getSessionId(), ticket.getRowNumber(), ticket.getPlaceNumber());
        if (requiredTicket.isPresent()) {
            model.addAttribute("ticket", ticket);
            return "tickets/error";
        }
        Ticket saveTicket = ticketService.save(ticket);
        model.addAttribute("ticket", saveTicket);

        return "tickets/order";
    }

}