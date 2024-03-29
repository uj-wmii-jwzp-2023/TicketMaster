package uj.jwzp.ticketmaster.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.exceptions.EntityAlreadyExistsException;
import uj.jwzp.ticketmaster.exceptions.EntityNotExistsException;
import uj.jwzp.ticketmaster.repositories.LocationRepository;


@Service
public class LocationService {
    @Autowired
    private final LocationRepository repository;

    public LocationService(LocationRepository locationRepository) {
        this.repository = locationRepository;
    }

    public List<Location> getAllLocations() {
        return repository.findAll();
    }

    public Location getLocationById(long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotExistsException(id));
    }

    public Location addNewLocation(Location newLocation) {
        Location location = repository.findByName(newLocation.getName());
        if (location != null)
            throw new EntityAlreadyExistsException("name", newLocation.getName());
        repository.save(newLocation);

        return newLocation;
    }

    public void deleteLocation(long id) {
        repository.deleteById(id);
    }

    public Location updateLocation(long id, Location updatedLocation) {
        Location location = repository.findById(id).orElseThrow(() -> new EntityNotExistsException(id));
        location.setName(updatedLocation.getName());
        repository.save(location);

        return location;
    }
}
