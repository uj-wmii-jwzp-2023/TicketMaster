package uj.jwzp.ticketmaster;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import uj.jwzp.ticketmaster.controllers.LocationController;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.repositories.LocationRepository;

import java.util.ArrayList;
import java.util.List;



@WebMvcTest(LocationController.class)
public class LocationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationRepository locationRepository;

    private LocationController locationController;

    @BeforeEach
    public void setup(){
        locationController = new LocationController(locationRepository);
    }

    @Test
    public void testGetLocations() {
        Location location1 = new Location("Tauron Arena");
        Location location2 = new Location("Ergo Arena");
        List<Location> locationList = new ArrayList<>();
        locationList.add(location1);
        locationList.add(location2);

        Mockito.when(locationRepository.findAll()).thenReturn(locationList);

        Assertions.assertSame(locationController.getLocations(), locationList);
    }

    @Test
    public void testGetLocationsById() {
        Location location1 = new Location("Tauron Arena");
        Location location2 = new Location("Ergo Arena");

        Mockito.when(locationRepository.findById(1)).thenReturn(location1);
        Mockito.when(locationRepository.findById(2)).thenReturn(location2);

        Assertions.assertSame(locationController.getById(1).getBody(), location1);
        Assertions.assertSame(locationController.getById(2).getBody(), location2);
    }
}
