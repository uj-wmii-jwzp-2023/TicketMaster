package uj.jwzp.ticketmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.jwzp.ticketmaster.entities.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    Concert findById(long id);
    void deleteById(long id);
}
