package uj.jwzp.ticketmaster.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uj.jwzp.ticketmaster.entities.Concert;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.entities.Ticket;
import uj.jwzp.ticketmaster.entities.TicketPool;
import uj.jwzp.ticketmaster.exceptions.EntityNotExistsException;
import uj.jwzp.ticketmaster.repositories.ConcertRepository;
import uj.jwzp.ticketmaster.repositories.LocationRepository;
import uj.jwzp.ticketmaster.repositories.TicketPoolRepository;
import uj.jwzp.ticketmaster.repositories.TicketRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final LocationRepository locationRepository;
    @Autowired
    private final TicketPoolRepository ticketPoolRepository;
    @Autowired
    private final ConcertRepository concertRepository;

    public TicketService(TicketRepository ticketRepository, TicketPoolRepository ticketPoolRepository,
                         LocationRepository locationRepository, ConcertRepository concertRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketPoolRepository = ticketPoolRepository;
        this.locationRepository = locationRepository;
        this.concertRepository = concertRepository;
    }

    public List<Ticket> getAllTickets(long locationId, long concertId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new EntityNotExistsException(concertId));

        List<TicketPool> ticketPoolList = ticketPoolRepository.findByConcert_Id(concertId);

        List<Ticket> ticketList = new ArrayList<>();
        for (TicketPool ticketPool : ticketPoolList) {
            ticketList.addAll(ticketRepository.findById(ticketPool.getId()).stream().toList());
        }

        return ticketList;
    }
}
