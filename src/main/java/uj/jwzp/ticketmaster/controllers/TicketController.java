package uj.jwzp.ticketmaster.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.entities.Ticket;
import uj.jwzp.ticketmaster.services.TicketService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/locations/{locationId}/concerts/{concertId}/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Ticket> getTickets(@PathVariable long locationId, @PathVariable long concertId){
        return ticketService.getAllTickets(locationId, concertId);
    }

    @PostMapping("/{locationZoneId}/reservation")
    public String reserveTicket(@PathVariable long locationId, @PathVariable long concertId,
                                                @PathVariable long locationZoneId, Principal principal){
        return ticketService.reserveTicket(locationId, concertId, locationZoneId, principal);
    }

    @PostMapping("/{locationZoneId}/purchase")
    public Ticket purchaseTicket(@PathVariable long locationId, @PathVariable long concertId,
                                                 @PathVariable long locationZoneId, Principal principal){
        return ticketService.purchaseTicket(locationId, concertId, locationZoneId, principal);
    }
}
