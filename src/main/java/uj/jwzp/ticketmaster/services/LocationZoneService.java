package uj.jwzp.ticketmaster.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.entities.LocationZone;
import uj.jwzp.ticketmaster.exceptions.EntityNotExistsException;
import uj.jwzp.ticketmaster.repositories.LocationRepository;
import uj.jwzp.ticketmaster.repositories.LocationZoneRepository;

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

    public List<LocationZone> getLocationZones(long locationId) {
        Optional<Location> location = locationRepository.findById(locationId);
        if (location.isEmpty())
            throw new EntityNotExistsException(locationId);
        return repository.findByLocationId(locationId);
    }

    public LocationZone addNewZone(long locationId, LocationZone locationZone) {
        Optional<Location> location = locationRepository.findById(locationId);
        if (location.isEmpty())
            throw new EntityNotExistsException(locationId);
        locationZone.setLocation(location.orElse(null));
        repository.save(locationZone);

        return locationZone;
    }

    public void deleteZone(Long zoneId) {
        repository.deleteById(zoneId);
    }
}
