package uj.jwzp.ticketmaster.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.services.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping()
    public List<Location> getLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable long id) {
        Location location = locationService.getLocationById(id);
        return ResponseEntity.ok().body(location);
    }

    @PostMapping()
    public ResponseEntity<String> addLocation(@RequestBody Location newLocation) {
        locationService.addNewLocation(newLocation);
        return ResponseEntity.ok().body("Location has been added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok().body("Location has been deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateLocation(@PathVariable long id, @RequestBody Location updatedLocation) {
        locationService.updateLocation(id, updatedLocation);
        return ResponseEntity.ok().body("Location has been updated");
    }
}
