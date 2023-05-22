package uj.jwzp.ticketmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.jwzp.ticketmaster.entities.TicketPool;

import java.util.List;

//TODO findByConcert_Id nie działa
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
    List<TicketPool> findByConcert_Id(long id);
}
