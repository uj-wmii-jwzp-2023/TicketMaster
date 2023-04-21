package uj.jwzp.ticketmaster.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.entities.Ticket;
import uj.jwzp.ticketmaster.services.TicketService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/locations/{locationId}/events/{eventId}/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping()
    public List<Ticket> getTickets(@PathVariable long locationId, @PathVariable long eventId){
        return null;
    }

    @PostMapping("/{ticketId}/reservation")
    public ResponseEntity<String> reserveTicket(@PathVariable long locationId, @PathVariable long eventId,
                                                @PathVariable long ticketId, Principal principal){
        return ResponseEntity.ok().body("Reservation was successful");
    }

    @PostMapping("/{ticketId}/purchase")
    public ResponseEntity<String> purchaseTicket(@PathVariable long locationId, @PathVariable long eventId,
                                                 @PathVariable long ticketId, Principal principal){
        return ResponseEntity.ok().body("Purchase was successful");
    }
}
