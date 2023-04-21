package uj.jwzp.ticketmaster.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.exceptions.LocationAlreadyExistsException;
import uj.jwzp.ticketmaster.exceptions.LocationNotExistsException;
import uj.jwzp.ticketmaster.repositories.LocationRepository;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    private final LocationRepository repository;


    public LocationService(LocationRepository locationRepository) {
        this.repository = locationRepository;
    }

    public List<Location> getAllLocations(){
        return repository.findAll();
    }

    public Location getLocationById(long id){
        Location location = repository.findById(id);
        if (location == null)
            throw new LocationNotExistsException("id: " + id);
        return location;
    }

    public void addNewLocation(Location newLocation){
        Location location = repository.findByName(newLocation.getName());
        if (location != null)
            throw new LocationAlreadyExistsException("name: " + newLocation.getName());
        repository.save(newLocation);
    }

    public void deleteLocation(long id){
        repository.deleteById(id);
    }

    public void updateLocation(long id, Location updatedLocation){
        Location location = repository.findById(id);
        if (location == null)
            throw new LocationNotExistsException("id: " + id);
        location.setName(updatedLocation.getName());
        repository.save(location);
    }
}