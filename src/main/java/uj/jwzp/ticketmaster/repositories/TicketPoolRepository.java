package uj.jwzp.ticketmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.jwzp.ticketmaster.entities.TicketPool;

public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
    TicketPool findById(long id);
    void deleteById(long id);
}
