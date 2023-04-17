package uj.jwzp.ticketmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.jwzp.ticketmaster.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findById(long id);
    void deleteById(long id);
}
