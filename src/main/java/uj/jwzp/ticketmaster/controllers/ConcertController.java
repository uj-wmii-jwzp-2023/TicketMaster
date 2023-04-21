package uj.jwzp.ticketmaster.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.entities.Concert;
import uj.jwzp.ticketmaster.services.ConcertService;

import java.util.List;

@RestController
@RequestMapping("/locations/{locationId}/events")
public class ConcertController {
    private final ConcertService concertService;

    public ConcertController(ConcertService concertService){
        this.concertService= concertService;
    }

    @GetMapping()
    public List<Concert> getConcerts(@PathVariable long locationId){
        return concertService.getConcerts(locationId);
    }

    @PostMapping()
    public ResponseEntity<String> addConcert(@PathVariable long locationId, @RequestBody Concert newConcert){
        concertService.addConcert(locationId, newConcert);
        return ResponseEntity.ok().body("Concert has been added");
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<Concert> getConcertDetails(@PathVariable long locationId, @PathVariable long concertId){
        return ResponseEntity.ok().body(concertService.getConcertDetails(locationId, concertId));
    }

    @DeleteMapping("/{concertId}")
    public ResponseEntity<String> deleteConcert(@PathVariable long locationId, @PathVariable long concertId){
        concertService.deleteConcert(locationId, concertId);
        return ResponseEntity.ok().body("Concert has been deleted");
    }
}
