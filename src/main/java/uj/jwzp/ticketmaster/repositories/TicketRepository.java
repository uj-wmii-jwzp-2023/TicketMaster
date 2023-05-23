package uj.jwzp.ticketmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.jwzp.ticketmaster.entities.Ticket;
import uj.jwzp.ticketmaster.entities.User;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTicketPoolId(long id);
    List<Ticket> findByReservedBy(User user);
}
