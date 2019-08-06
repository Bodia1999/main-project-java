package com.academy.project.demo.controller;

import com.academy.project.demo.dto.response.event.MainInfoTicketResponse;
import com.academy.project.demo.dto.response.ticket.TicketResponse;
import com.academy.project.demo.service.TicketEvolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/ticketEvolution")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketEvolutionController {
    private final TicketEvolutionService ticketEvolutionService;

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public MainInfoTicketResponse get() throws IOException {
        return ticketEvolutionService.getAllEventsByQuery();
    }

    @GetMapping("/getTicket/{ticketId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public TicketResponse get(@PathVariable String ticketId) throws IOException {
        return ticketEvolutionService.getInfoAboutTicket(ticketId);
    }



}
