package uj.jwzp.ticketmaster.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uj.jwzp.ticketmaster.entities.*;
import uj.jwzp.ticketmaster.exceptions.EntityNotExistsException;
import uj.jwzp.ticketmaster.exceptions.NoTicketsLeftException;
import uj.jwzp.ticketmaster.exceptions.NotEnoughCashException;
import uj.jwzp.ticketmaster.exceptions.TicketPurchaseException;
import uj.jwzp.ticketmaster.repositories.*;

import java.security.Principal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    private final TicketRepository ticketRepository;
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
                         ConcertRepository concertRepository, Clock clock,
                         LocationZoneRepository locationZoneRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketPoolRepository = ticketPoolRepository;
        this.concertRepository = concertRepository;
        this.clock = clock;
        this.locationZoneRepository = locationZoneRepository;
        this.userRepository = userRepository;
    }

    public List<Ticket> getAllTickets(long concertId) {
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new EntityNotExistsException(concertId));

        List<TicketPool> ticketPoolList = ticketPoolRepository.findByConcertId(concertId);

        List<Ticket> ticketList = new ArrayList<>();
        for (TicketPool ticketPool : ticketPoolList) {
            ticketList.addAll(ticketRepository.findByTicketPoolId(ticketPool.getId()).stream().toList());
        }

        return ticketList;
    }

    //TODO: ticketsLeft decremental in trigger?
    public String reserveTicket(long concertId, long locationZoneId, Principal principal) {
        TicketPool ticketPool = getTicketPool(concertId, locationZoneId);

        User user = userRepository.findByUsername(principal.getName()).get();

        Ticket ticket = new Ticket(ticketPool, user, null, LocalDateTime.now(clock), null);

        ticketPool.setTicketsLeft(ticketPool.getTicketsLeft() - 1);

        ticketPoolRepository.save(ticketPool);

        ticketRepository.save(ticket);

        return "Reservation was successful";
    }

    //TODO: user balance update in trigger?
    public Ticket purchaseTicket(long concertId, long locationZoneId, Principal principal) {
        TicketPool ticketPool = getTicketPool(concertId, locationZoneId);

        User user = userRepository.findByUsername(principal.getName()).get();

        List<Ticket> ticketList = ticketRepository.findByReservedBy(user).stream()
                .filter(ticket -> ticket.getTicketPool() == ticketPool && ticket.getPurchasedAt() == null).toList();

        if (ticketList.size() == 0) {
            throw new TicketPurchaseException();
        }

        if (ticketPool.getPrice().compareTo(user.getCash()) > 0) {
            throw new NotEnoughCashException(user.getCash(), ticketPool.getPrice());
        }

        Ticket purchasedTicket = ticketList.get(0);

        purchasedTicket.setPurchasedBy(user);
        purchasedTicket.setPurchasedAt(LocalDateTime.now(clock));

        user.setCash(user.getCash().subtract(ticketPool.getPrice()));

        userRepository.save(user);

        ticketRepository.save(purchasedTicket);

        return purchasedTicket;
    }

    private TicketPool getTicketPool(long concertId, long locationZoneId) {
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new EntityNotExistsException(concertId));
        LocationZone locationZone = locationZoneRepository.findById(locationZoneId).orElseThrow(() -> new EntityNotExistsException(locationZoneId));

        TicketPool ticketPool = ticketPoolRepository.findByConcertId(concertId).stream()
                .filter(currentTicketPool -> currentTicketPool.getLocationZone() == locationZone).toList().get(0);

        if (ticketPool.getTicketsLeft() == 0) {
            throw new NoTicketsLeftException(ticketPool.getId());
        }

        return ticketPool;
    }
}
