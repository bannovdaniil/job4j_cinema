package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.model.Ticket;
import ru.job4j.service.TicketService;

/**
 * Контроллер работы с Билетом
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
    public String orderTicket(@ModelAttribute Ticket ticket,
                              @RequestParam(value = "placeBox", defaultValue = "x") String placeBox,
                              Model model) {
        if (!"x".equals(placeBox) && placeBox.matches("^\\d+-\\d+$")) {
            String[] places = placeBox.split("-");
            ticket.setRowNumber(Integer.parseInt(places[0]));
            ticket.setPlaceNumber(Integer.parseInt(places[1]));
        }

        Ticket saveTicket = ticketService.save(ticket);
        model.addAttribute("ticket", saveTicket);

        return "tickets/order";
    }

}