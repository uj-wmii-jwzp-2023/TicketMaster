package uj.jwzp.ticketmaster.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.entities.LocationZone;
import uj.jwzp.ticketmaster.services.LocationZoneService;

import java.util.List;

@RestController
public class LocationZoneController {
    private final LocationZoneService locationZoneService;

    public LocationZoneController(LocationZoneService locationZoneService) {
        this.locationZoneService = locationZoneService;
    }

    @GetMapping("locations/{locationId}/zones")
    public List<LocationZone> getLocationZones(@PathVariable long locationId) {
        return locationZoneService.getLocationZones(locationId);
    }

    @PostMapping("locations/{locationId}/zones")
    public ResponseEntity<String> addZone(@PathVariable long locationId, @RequestBody LocationZone newZoneLocation) {
        locationZoneService.addNewZone(locationId, newZoneLocation);
        return ResponseEntity.ok().body("Zone has been added");
    }

    @DeleteMapping("locations/{locationId}/zones/{zoneId}")
    public ResponseEntity<String> deleteZone(@PathVariable long locationId, @PathVariable long zoneId) {
        locationZoneService.deleteZone(zoneId);
        return ResponseEntity.ok().body("Zone has been deleted");
    }
}
