package uj.jwzp.ticketmaster.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.exceptions.EntityAlreadyExistsException;
import uj.jwzp.ticketmaster.exceptions.EntityNotExistsException;
import uj.jwzp.ticketmaster.repositories.LocationRepository;
import uj.jwzp.ticketmaster.services.LocationService;

@SpringBootTest
@Import(FlywayAutoConfiguration.class)
public class LocationServiceTest {

	@Autowired
	private LocationService locationService;

	@MockBean
	private LocationRepository locationRepository;

	@Test
	public void testGetAllLocations() {
		// Given
		Location location1 = new Location("Location 1");
		Location location2 = new Location("Location 2");
		List<Location> locations = Arrays.asList(location1, location2);

		// When
		when(locationRepository.findAll()).thenReturn(locations);

		List<Location> result = locationService.getAllLocations();

		// Then
		assertThat(result)
			.hasSize(2)
			.contains(location1)
			.contains(location2);
	}

	@Test
	public void testGetLocation() {
		Location loc = new Location("loc");
		loc.setId(1);
		when(locationRepository.findById(loc.getId())).thenReturn(Optional.of(loc));

		var result = locationService.getLocationById(loc.getId());
		
		assertEquals(result.getId(), loc.getId());
		assertEquals(result.getName(), loc.getName());
	}

	@Test
	public void testGetNotExistingLocation() {
		Location loc = new Location("loc");
		loc.setId(1);
		when(locationRepository.findById(loc.getId())).thenReturn(Optional.empty());

		assertThrows(EntityNotExistsException.class, () -> {
			locationService.getLocationById(loc.getId());
		});
	}

	@Test
	public void testAddNewLocation() {
		Location loc = new Location("loc");
		when(locationRepository.findByName("loc")).thenReturn(null);

		locationService.addNewLocation(loc);
		
		verify(locationRepository, times(1)).save(loc);
	}

	@Test
	public void testAddExistingLocation() {
		Location loc = new Location("loc");
		when(locationRepository.findByName("loc")).thenReturn(loc);
		assertThrows(EntityAlreadyExistsException.class, () -> {
			locationService.addNewLocation(loc);
		});
	}
}
