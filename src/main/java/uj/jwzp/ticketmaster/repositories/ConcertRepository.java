package uj.jwzp.ticketmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.jwzp.ticketmaster.entities.Concert;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    Concert findById(long id);
    void deleteById(long id);

    List<Concert> findByLocation_Id(long id);
}