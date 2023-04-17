package uj.jwzp.ticketmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.jwzp.ticketmaster.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findById(long id);
    void deleteById(long id);
}
