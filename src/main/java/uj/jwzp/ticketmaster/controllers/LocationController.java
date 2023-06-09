package uj.jwzp.ticketmaster.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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
    public Location getById(@PathVariable long id) {
        return locationService.getLocationById(id);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public Location addLocation(@RequestBody Location newLocation) {
        return locationService.addNewLocation(newLocation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteLocation(@PathVariable long id) {
        locationService.deleteLocation(id);
        return "Location has been deleted";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Location updateLocation(@PathVariable long id, @RequestBody Location updatedLocation) {
        return locationService.updateLocation(id, updatedLocation);
    }
}
