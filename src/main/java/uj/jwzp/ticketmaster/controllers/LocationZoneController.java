package uj.jwzp.ticketmaster.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.entities.LocationZone;
import uj.jwzp.ticketmaster.services.LocationZoneService;

import java.util.List;

@RestController
@RequestMapping("/locations/{locationId}/zones")
public class LocationZoneController {
    private final LocationZoneService locationZoneService;

    public LocationZoneController(LocationZoneService locationZoneService) {
        this.locationZoneService = locationZoneService;
    }

    @GetMapping()
    public List<LocationZone> getLocationZones(@PathVariable long locationId) {
        return locationZoneService.getLocationZones(locationId);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public LocationZone addZone(@PathVariable long locationId, @RequestBody LocationZone newZoneLocation) {
        return locationZoneService.addNewZone(locationId, newZoneLocation);
    }

    @DeleteMapping("/{zoneId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteZone(@PathVariable long locationId, @PathVariable long zoneId) {
        locationZoneService.deleteZone(zoneId);
        return "Zone has been deleted";
    }
}
