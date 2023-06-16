package uj.jwzp.ticketmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.jwzp.ticketmaster.entities.LocationZone;

import java.util.List;

public interface LocationZoneRepository extends JpaRepository<LocationZone, Long> {
    List<LocationZone> findByLocationId(long id);
}
