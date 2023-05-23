package uj.jwzp.ticketmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.jwzp.ticketmaster.entities.TicketPool;

import java.util.List;

public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
    List<TicketPool> findByConcertId(long id);
}
