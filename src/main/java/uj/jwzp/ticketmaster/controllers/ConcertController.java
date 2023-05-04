package uj.jwzp.ticketmaster.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.ConcertSchema;
import uj.jwzp.ticketmaster.entities.Concert;
import uj.jwzp.ticketmaster.services.ConcertService;

@RestController
@RequestMapping("/locations/{locationId}/concerts")
public class ConcertController {
    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @GetMapping()
    public List<Concert> getConcerts(@PathVariable long locationId) {
        return concertService.getConcerts(locationId);
    }

    @PostMapping()
    public ResponseEntity<String> addConcert(@PathVariable long locationId, @RequestBody ConcertSchema newConcert) {
        concertService.addConcert(locationId, newConcert);
        return ResponseEntity.ok().body("Concert has been added");
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<Concert> getConcertDetails(@PathVariable long locationId, @PathVariable long concertId) {
        return ResponseEntity.ok().body(concertService.getConcertDetails(locationId, concertId));
    }

    @DeleteMapping("/{concertId}")
    public ResponseEntity<String> deleteConcert(@PathVariable long locationId, @PathVariable long concertId) {
        concertService.deleteConcert(locationId, concertId);
        return ResponseEntity.ok().body("Concert has been deleted");
    }
}
