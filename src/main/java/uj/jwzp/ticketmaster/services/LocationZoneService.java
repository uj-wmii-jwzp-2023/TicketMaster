package uj.jwzp.ticketmaster.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.entities.LocationZone;
import uj.jwzp.ticketmaster.exceptions.LocationNotExistsException;
import uj.jwzp.ticketmaster.repositories.LocationRepository;
import uj.jwzp.ticketmaster.repositories.LocationZoneRepository;

import java.util.Optional;

@Service
public class LocationZoneService {
    @Autowired
    private final LocationZoneRepository repository;

    @Autowired
    private final LocationRepository locationRepository;

    public LocationZoneService(LocationZoneRepository locationZoneRepository, LocationRepository locationRepository) {
        this.repository = locationZoneRepository;
        this.locationRepository = locationRepository;
    }

    public void addNewZone(Long locationId, LocationZone locationZone) {
        Optional<Location> location = locationRepository.findById(locationId);
        if (location.isEmpty())
            throw new LocationNotExistsException("id: " + locationId);
        locationZone.setLocation(location.orElse(null));
        repository.save(locationZone);
    }

    public void deleteZone(Long zoneId) {
        repository.deleteById(zoneId);
    }
}
