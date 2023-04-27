package uj.jwzp.ticketmaster.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uj.jwzp.ticketmaster.entities.Concert;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.exceptions.EntityNotExistsException;
import uj.jwzp.ticketmaster.repositories.ConcertRepository;
import uj.jwzp.ticketmaster.repositories.LocationRepository;

@Service
public class ConcertService {
    @Autowired
    private final ConcertRepository repository;

    @Autowired
    private final LocationRepository locationRepository;

    public ConcertService(ConcertRepository concertRepository, LocationRepository locationRepository) {
        this.repository = concertRepository;
        this.locationRepository = locationRepository;
    }

    public List<Concert> getConcerts(long locationId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));
        return repository.findByLocation_Id(locationId);
    }

    public void addConcert(long locationId, Concert concert) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));
        concert.setLocation(location);
        repository.save(concert);
    }

    public Concert getConcertDetails(long locationId, long concertId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));
        Concert concert = repository.findById(concertId).orElseThrow(() -> new EntityNotExistsException(concertId));
        return concert;
    }

    public void deleteConcert(long locationId, long concertId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));
        Concert concert = repository.findById(concertId).orElseThrow(() -> new EntityNotExistsException(concertId));
        repository.deleteById(concertId);
    }
}
