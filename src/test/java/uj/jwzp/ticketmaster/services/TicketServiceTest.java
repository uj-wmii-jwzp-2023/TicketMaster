package uj.jwzp.ticketmaster.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Clock;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import uj.jwzp.ticketmaster.entities.Concert;
import uj.jwzp.ticketmaster.entities.LocationZone;
import uj.jwzp.ticketmaster.entities.Ticket;
import uj.jwzp.ticketmaster.entities.TicketPool;
import uj.jwzp.ticketmaster.entities.User;
import uj.jwzp.ticketmaster.entities.UserRole;
import uj.jwzp.ticketmaster.exceptions.NotEnoughCashException;
import uj.jwzp.ticketmaster.repositories.ConcertRepository;
import uj.jwzp.ticketmaster.repositories.LocationZoneRepository;
import uj.jwzp.ticketmaster.repositories.TicketPoolRepository;
import uj.jwzp.ticketmaster.repositories.TicketRepository;
import uj.jwzp.ticketmaster.repositories.UserRepository;

@SpringBootTest
@Import(FlywayAutoConfiguration.class)
public class TicketServiceTest {

	@Autowired
	private TicketService ticketService;

	@MockBean
    private TicketRepository ticketRepository;
    @MockBean
    private TicketPoolRepository ticketPoolRepository;
    @MockBean
    private ConcertRepository concertRepository;
    @MockBean
    private LocationZoneRepository locationZoneRepository;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private Clock clock;


	@Mock
	private Principal principal;

	private User user;


	private final static String TEST_USERNAME = "username";
	private final long LOC_ZONE_ID = 5;
	private final long CONCERT_ID = 8;
	private final long TICKET_POOL_ID = 10;
	private final BigDecimal TICKET_PRICE = BigDecimal.valueOf(99.99);

	@Captor
	ArgumentCaptor<Concert> savedConcert;

	@BeforeEach
	void setUp() {
		var locationZone = new LocationZone(null, "VIP", "V", 1);
		locationZone.setId(LOC_ZONE_ID);
		when(locationZoneRepository.findById(LOC_ZONE_ID)).thenReturn(Optional.of(locationZone));

		var concert = new Concert();
		when(concertRepository.findById(CONCERT_ID)).thenReturn(Optional.of(concert));

		var ticketPool = new TicketPool(concert, locationZone, TICKET_PRICE, 1);
		when(ticketPoolRepository.findById(TICKET_POOL_ID)).thenReturn(Optional.of(ticketPool));
		when(ticketPoolRepository.findByConcertId(CONCERT_ID)).thenReturn(List.of(ticketPool));

		when(principal.getName()).thenReturn(TEST_USERNAME);
		user = new User();
		user.setId(1l);
		user.setUsername(TEST_USERNAME);
		user.setStatus(UserRole.USER);
		user.setCash(BigDecimal.ZERO);
		when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));

		var ticket = new Ticket(ticketPool, user, null, null, null);
		when(ticketRepository.findByReservedBy(user)).thenReturn(List.of(ticket));
	}

	@Test
	void testPurchaseWithoutCash() {
		assertThrows(NotEnoughCashException.class, () -> {
			ticketService.purchaseTicket(CONCERT_ID, LOC_ZONE_ID, principal);
		});
	}

	@Test
	void testPurchaseLessCash() {
		user.setCash(TICKET_PRICE.subtract(BigDecimal.valueOf(0.01)));
		assertThrows(NotEnoughCashException.class, () -> {
			ticketService.purchaseTicket(CONCERT_ID, LOC_ZONE_ID, principal);
		});
	}

	@Test
	void testPurchaseWithExactCash() {
		user.setCash(TICKET_PRICE);
		ticketService.reserveTicket(CONCERT_ID, LOC_ZONE_ID, principal);
		var result = ticketService.purchaseTicket(CONCERT_ID, LOC_ZONE_ID, principal);
		assertThat(result)
			.isNotNull();
		assertEquals(user, result.getPurchasedBy());
	}


	@Test
	void testPurchaseWithMoreCash() {
		user.setCash(TICKET_PRICE.add(BigDecimal.valueOf(0.01)));
		ticketService.reserveTicket(CONCERT_ID, LOC_ZONE_ID, principal);
		var result = ticketService.purchaseTicket(CONCERT_ID, LOC_ZONE_ID, principal);
		assertThat(result)
			.isNotNull();
		assertEquals(user, result.getPurchasedBy());
	}

}
