package uj.jwzp.ticketmaster.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import uj.jwzp.ticketmaster.ConcertSchema;
import uj.jwzp.ticketmaster.entities.Concert;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.entities.LocationZone;
import uj.jwzp.ticketmaster.repositories.ConcertRepository;
import uj.jwzp.ticketmaster.repositories.LocationRepository;
import uj.jwzp.ticketmaster.repositories.LocationZoneRepository;
import uj.jwzp.ticketmaster.repositories.TicketPoolRepository;

@SpringBootTest
@Import(FlywayAutoConfiguration.class)
public class ConcertServiceTest {

	@Autowired
	private ConcertService concertService;

	@MockBean
    private ConcertRepository repository;
    @MockBean
    private LocationRepository locationRepository;
    @MockBean
    private LocationZoneRepository locationZoneRepository;
    @MockBean
    private TicketPoolRepository ticketPoolRepository;

	private final long LOCATION_ID = 5;
	private final String CONCERT_NAME = "Concerto";

	@Captor
	ArgumentCaptor<Concert> savedConcert;

	@BeforeEach
	void setUp() {
		var location = new Location("loc");
		when(locationRepository.findById(LOCATION_ID)).thenReturn(Optional.of(location));
		
		var locationZones = List.of(
			new LocationZone(location, "Zone A", "A", 5),
			new LocationZone(location, "Zone B", "B", 7),
			new LocationZone(location, "Zone C", "C", 11)
		);
		when(locationZoneRepository.findByLocation_Id(LOCATION_ID)).thenReturn(locationZones);
	}

	@Test
	public void testConcertAddedWithAllZones() {
		var pricesList = List.of(
			BigDecimal.valueOf(2.50),
			BigDecimal.valueOf(5.00),
			BigDecimal.valueOf(3.99)
		);
		ConcertSchema concertSchema = new ConcertSchema(CONCERT_NAME, pricesList);

		var newConcert = concertService.addConcert(LOCATION_ID, concertSchema);

		verify(repository).save(savedConcert.capture());
		assertThat(savedConcert.getValue())
			.isNotNull()
			.returns(CONCERT_NAME, from(Concert::getName))
			.isSameAs(newConcert)
			;
		verify(ticketPoolRepository, times(pricesList.size())).save(any());
	}

}
