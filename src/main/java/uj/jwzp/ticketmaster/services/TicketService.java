package uj.jwzp.ticketmaster.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uj.jwzp.ticketmaster.entities.*;
import uj.jwzp.ticketmaster.exceptions.EntityNotExistsException;
import uj.jwzp.ticketmaster.repositories.*;

import java.security.Principal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TODO errors in reserveTicket and purchaseTicket
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
    @Autowired
    private final LocationZoneRepository locationZoneRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final Clock clock;

    public TicketService(TicketRepository ticketRepository, TicketPoolRepository ticketPoolRepository,
                         LocationRepository locationRepository, ConcertRepository concertRepository, Clock clock,
                         LocationZoneRepository locationZoneRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketPoolRepository = ticketPoolRepository;
        this.locationRepository = locationRepository;
        this.concertRepository = concertRepository;
        this.clock = clock;
        this.locationZoneRepository = locationZoneRepository;
        this.userRepository = userRepository;
    }

    public List<Ticket> getAllTickets(long locationId, long concertId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new EntityNotExistsException(concertId));

        List<TicketPool> ticketPoolList = ticketPoolRepository.findByConcertId(concertId);

        List<Ticket> ticketList = new ArrayList<>();
        for (TicketPool ticketPool : ticketPoolList) {
            ticketList.addAll(ticketRepository.findByTicketPoolId(ticketPool.getId()).stream().toList());
        }

        return ticketList;
    }

    public String reserveTicket(long locationId, long concertId, long locationZoneId, Principal principal) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new EntityNotExistsException(concertId));
        LocationZone locationZone = locationZoneRepository.findById(locationZoneId).orElseThrow(() -> new EntityNotExistsException(locationZoneId));

        if (concert.getLocation() != location) {
            throw new EntityNotExistsException(locationZoneId);
        }

        if (locationZone.getLocation() != location) {
            throw new EntityNotExistsException(locationZoneId);
        }

        List<TicketPool> ticketPool = ticketPoolRepository.findByConcertId(concertId).stream()
                .filter(ticketPool1 -> ticketPool1.getLocationZone() == locationZone).toList();

        if (ticketPool.get(0).getTicketsLeft() == 0) {
            throw new EntityNotExistsException(locationZoneId);
        }

        User user = userRepository.findByUsername(principal.getName()).get();

        Ticket ticket = new Ticket(ticketPool.get(0), user, null, LocalDateTime.now(clock), null);

        ticketPool.get(0).setTicketsLeft(ticketPool.get(0).getTicketsLeft() - 1);

        ticketPoolRepository.save(ticketPool.get(0));

        ticketRepository.save(ticket);

        return "Reservation was successful";
    }

    public Ticket purchaseTicket(long locationId, long concertId, long locationZoneId, Principal principal){
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotExistsException(locationId));
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new EntityNotExistsException(concertId));
        LocationZone locationZone = locationZoneRepository.findById(locationZoneId).orElseThrow(() -> new EntityNotExistsException(locationZoneId));

        if (concert.getLocation() != location) {
            throw new EntityNotExistsException(locationZoneId);
        }

        if (locationZone.getLocation() != location) {
            throw new EntityNotExistsException(locationZoneId);
        }

        List<TicketPool> ticketPool = ticketPoolRepository.findByConcertId(concertId).stream()
                .filter(ticketPool1 -> ticketPool1.getLocationZone() == locationZone).toList();

        if (ticketPool.get(0).getTicketsLeft() == 0) {
            throw new EntityNotExistsException(locationZoneId);
        }

        User user = userRepository.findByUsername(principal.getName()).get();

        if (ticketPool.get(0).getPrice().compareTo(user.getCash()) == 1) {
            throw new EntityNotExistsException(locationId);
        }

        List<Ticket> ticketList = ticketRepository.findByReservedBy(user).stream().filter(ticket -> ticket.getTicketPool() == ticketPool.get(0)).toList();

        if (ticketList.size() == 0) {
            throw new EntityNotExistsException(locationZoneId);
        }

        ticketList.get(0).setPurchasedBy(user);
        ticketList.get(0).setPurchasedAt(LocalDateTime.now(clock));

        ticketPool.get(0).setTicketsLeft(ticketPool.get(0).getTicketsLeft() - 1);

        ticketPoolRepository.save(ticketPool.get(0));

        ticketRepository.save(ticketList.get(0));

        return ticketList.get(0);
    }
}
