package uj.jwzp.ticketmaster.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.repositories.LocationRepository;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationRepository repository;

    LocationController(LocationRepository repository){
        this.repository = repository;
    }

    @GetMapping()
    public List<Location> getLocations(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable long id){
        Location location = repository.findById(id);
        if (location != null)
            return ResponseEntity.ok().body(location);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<String> addLocation(@RequestBody Location newLocation){
        repository.save(newLocation);
        return ResponseEntity.ok().body("Location has benn added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable long id){
        repository.deleteById(id);
        return ResponseEntity.ok().body("Location has been deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateLocation(@PathVariable long id, @RequestBody Location updatedLocation){
        Location location = repository.findById(id);
        if (location == null)
            return ResponseEntity.notFound().build();
        location.setId(updatedLocation.getId());
        location.setName(updatedLocation.getName());
        repository.save(location);
        return ResponseEntity.ok().body("Location has been updated");
    }
}
