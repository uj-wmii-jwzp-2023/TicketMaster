package uj.jwzp.ticketmaster.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uj.jwzp.ticketmaster.entities.Location;
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
        return repository.findById(id);
    }

    public void addNewLocation(Location location){
        repository.save(location);
    }

    public void deleteLocation(long id){
        repository.deleteById(id);
    }

    public Location updateLocation(long id, Location updatedLocation){
        Location location = repository.findById(id);
        if (location == null)
            return null;
        location.setName(updatedLocation.getName());
        return repository.save(location);
    }
}
