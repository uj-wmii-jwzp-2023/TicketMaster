package uj.jwzp.ticketmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.jwzp.ticketmaster.entities.LocationZone;

public interface LocationZoneRepository extends JpaRepository<LocationZone, Long> {
}
