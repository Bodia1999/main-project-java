package com.academy.project.demo.controller;

import com.academy.project.demo.dto.request.ticket.evolution.TicketEvolutionRequest;
import com.academy.project.demo.dto.response.evolution.categories.CategoriesResponse;
import com.academy.project.demo.dto.response.evolution.event.MainInfoTicketResponse;
import com.academy.project.demo.dto.response.evolution.tickets.TicketGroupResponse;
import com.academy.project.demo.dto.response.evolution.tickets.TicketResponse;
import com.academy.project.demo.service.TicketEvolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/ticketEvolution")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketEvolutionController {
    private final TicketEvolutionService ticketEvolutionService;

    @PostMapping("/get")
    public MainInfoTicketResponse get(@RequestBody TicketEvolutionRequest ticketEvolutionRequest) throws Exception {
        return ticketEvolutionService.getAllEventsByQuery(ticketEvolutionRequest);
    }

    @GetMapping("/getEvent/{eventId}")
    public TicketResponse getInfoAboutEvent(@PathVariable String eventId) throws Exception {
        return ticketEvolutionService.getInfoAboutEvent(eventId);
    }

    @GetMapping("/getCategories")
    public CategoriesResponse getCategories() throws Exception {
        return ticketEvolutionService.getCategories();
    }

    @GetMapping("/getTickets/{ticketId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public TicketGroupResponse getInfoAboutTickets(@PathVariable String ticketId) throws Exception {
        return ticketEvolutionService.getInfoAboutTickets(ticketId);
    }

    @PostMapping("/createOrder")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public void createOrder() throws Exception {
        ticketEvolutionService.createOrder();
    }

    @PostMapping("/createCreditCard")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String createCreditCard() throws Exception {
       return ticketEvolutionService.createCreditCard();
    }



}
