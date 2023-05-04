package uj.jwzp.ticketmaster.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uj.jwzp.ticketmaster.ConcertSchema;
import uj.jwzp.ticketmaster.entities.Concert;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.entities.LocationZone;
import uj.jwzp.ticketmaster.entities.TicketPool;
import uj.jwzp.ticketmaster.exceptions.EntityNotExistsException;
import uj.jwzp.ticketmaster.repositories.ConcertRepository;
import uj.jwzp.ticketmaster.repositories.LocationRepository;
import uj.jwzp.ticketmaster.repositories.LocationZoneRepository;
import uj.jwzp.ticketmaster.repositories.TicketPoolRepository;

@Service
public class ConcertService {
    @Autowired
    private final ConcertRepository repository;

    @Autowired
    private final LocationRepository locationRepository;

    @Autowired
    private final LocationZoneRepository locationZoneRepository;

    @Autowired
    private final TicketPoolRepository ticketPoolRepository;

    public ConcertService(ConcertRepository concertRepository, LocationRepository locationRepository,
                          TicketPoolRepository ticketPoolRepository, LocationZoneRepository locationZoneRepository) {
        this.repository = concertRepository;
        this.locationRepository = locationRepository;
        this.ticketPoolRepository = ticketPoolRepository;
        this.locationZoneRepository = locationZoneRepository;
    }

    public List<Concert> getConcerts(long locationId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));
        return repository.findByLocation_Id(locationId);
    }

    public void addConcert(long locationId, ConcertSchema concert) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));

        Concert newConcert = new Concert();
        newConcert.setLocation(location);
        newConcert.setName(concert.name());

        List<LocationZone> locationZones = locationZoneRepository.findByLocation_Id(locationId);
        int i = 0;

        for (BigDecimal price : concert.pricesList()) {
            if (locationZones.size() < i + 1)
                break;
            ticketPoolRepository.save(new TicketPool(newConcert, locationZones.get(i), price));
            i++;
        }

        repository.save(newConcert);
    }

    public Concert getConcertDetails(long locationId, long concertId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));
        return repository.findById(concertId).orElseThrow(() -> new EntityNotExistsException(concertId));
    }

    public void deleteConcert(long locationId, long concertId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));
        Concert concert = repository.findById(concertId).orElseThrow(() -> new EntityNotExistsException(concertId));
        repository.deleteById(concertId);
    }
}
