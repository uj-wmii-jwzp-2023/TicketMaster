package uj.jwzp.ticketmaster.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.security.Principal;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import uj.jwzp.ticketmaster.entities.User;
import uj.jwzp.ticketmaster.entities.UserRole;
import uj.jwzp.ticketmaster.repositories.TicketRepository;
import uj.jwzp.ticketmaster.repositories.UserRepository;

@SpringBootTest
@Import(FlywayAutoConfiguration.class)
public class UserServiceTest {

	private final static String TEST_USERNAME = "username";

	@Autowired
	private UserService userService;

	@MockBean
    private UserRepository repository;
    @MockBean
    private TicketRepository ticketRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

	@Mock
	private Principal principal;

	private User user;

	@Captor
	ArgumentCaptor<User> savedUser;

	@BeforeEach
	void setUp() {
		when(principal.getName()).thenReturn(TEST_USERNAME);

		user = new User();
		user.setId(1l);
		user.setUsername(TEST_USERNAME);
		user.setStatus(UserRole.USER);
		user.setCash(BigDecimal.ONE);
	}

	@Test
	public void testUserWalletOperations() {
		when(repository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
		var resultInitial = userService.getWalletStatus(principal);
		assertTrue(user.getCash().compareTo(resultInitial) == 0);

		userService.addCash(BigDecimal.valueOf(12.99), principal);

		verify(repository).save(savedUser.capture());

		var expected = BigDecimal.valueOf(13.99);
		assertTrue(savedUser.getValue().getCash().compareTo(expected) == 0);
	}

}
