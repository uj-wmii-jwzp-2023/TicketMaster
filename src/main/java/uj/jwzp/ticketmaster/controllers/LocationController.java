package uj.jwzp.ticketmaster.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.services.LocationService;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping()
    public List<Location> getLocations(){
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable long id){
        Location location = locationService.getLocationById(id);
        if (location != null)
            return ResponseEntity.ok().body(location);
        else return ResponseEntity.status(404).body("Location with id " + id + " does not exist");
    }

    @PostMapping()
    public ResponseEntity<String> addLocation(@RequestBody Location newLocation){
        locationService.addNewLocation(newLocation);
        return ResponseEntity.ok().body("Location has been added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable long id){
        locationService.deleteLocation(id);
        return ResponseEntity.ok().body("Location has been deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateLocation(@PathVariable long id, @RequestBody Location updatedLocation){
        if (locationService.updateLocation(id, updatedLocation) == null){
            return ResponseEntity.status(404).body("Location with id " + id + " does not exist");
        }
        return ResponseEntity.ok().body("Location has been updated");
    }
}
