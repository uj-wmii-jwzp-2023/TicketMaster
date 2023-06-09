package uj.jwzp.ticketmaster.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.entities.Ticket;
import uj.jwzp.ticketmaster.services.TicketService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/concerts/{concertId}/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Ticket> getTickets(@PathVariable long concertId){
        return ticketService.getAllTickets(concertId);
    }

    @PostMapping("/{locationZoneId}/reservation")
    public String reserveTicket(@PathVariable long concertId, @PathVariable long locationZoneId, Principal principal){
        return ticketService.reserveTicket(concertId, locationZoneId, principal);
    }

    @PostMapping("/{locationZoneId}/purchase")
    public Ticket purchaseTicket(@PathVariable long concertId, @PathVariable long locationZoneId, Principal principal){
        return ticketService.purchaseTicket(concertId, locationZoneId, principal);
    }
}
