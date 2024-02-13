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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.job4j.model.Ticket;
import ru.job4j.service.TicketService;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TicketControllerTest {
    MockMvc mvc;
    @Mock
    private TicketService mockTicketService;
    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @DisplayName("Order ticket from placeBox - ok")
    @Test
    void orderTicketFromPlaceBoxThenOk() throws Exception {
        int expectedSessionId = 2;
        int expectedUserId = 3;
        int expectedRowNumber = 4;
        int expectedPlaceNumber = 5;
        int placeRow = 6;
        int placeNumber = 7;
        String expectedPlaceBox = placeRow + "-" + placeNumber;

        Ticket ticket = new Ticket(
                123,
                expectedSessionId,
                expectedRowNumber,
                expectedPlaceNumber,
                expectedUserId
        );

        Mockito.doReturn(Optional.empty()).when(mockTicketService).findByPlace(expectedSessionId, placeRow, placeNumber);
        Mockito.doReturn(ticket).when(mockTicketService).save(Mockito.any());

        mvc.perform(post("/tickets/order")
                        .content("sessionId=" + expectedSessionId
                                + "&userId=" + expectedUserId
                                + "&rowNumber=" + expectedRowNumber
                                + "&placeNumber=" + expectedPlaceNumber
                                + "&placeBox=" + expectedPlaceBox)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("tickets/order"))
                .andExpect(model().attribute("ticket", ticket));

        Mockito.verify(mockTicketService, Mockito.times(1)).save(Mockito.any());
    }

    @DisplayName("Order ticket from List - ok.")
    @Test
    void orderTicketFromListThenOk() throws Exception {
        int expectedSessionId = 2;
        int expectedUserId = 3;
        int expectedRowNumber = 4;
        int expectedPlaceNumber = 5;

        Ticket ticket = new Ticket(
                123,
                expectedSessionId,
                expectedRowNumber,
                expectedPlaceNumber,
                expectedUserId
        );

        Mockito.doReturn(Optional.empty()).when(mockTicketService).findByPlace(expectedSessionId, expectedRowNumber, expectedPlaceNumber);
        Mockito.doReturn(ticket).when(mockTicketService).save(Mockito.any());

        mvc.perform(post("/tickets/order")
                        .content("sessionId=" + expectedSessionId
                                + "&userId=" + expectedUserId
                                + "&rowNumber=" + expectedRowNumber
                                + "&placeNumber=" + expectedPlaceNumber)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("tickets/order"))
                .andExpect(model().attribute("ticket", ticket));

        Mockito.verify(mockTicketService, Mockito.times(1)).save(Mockito.any());
    }

}