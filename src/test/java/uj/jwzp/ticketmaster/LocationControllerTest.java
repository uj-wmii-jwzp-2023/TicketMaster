package uj.jwzp.ticketmaster;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import uj.jwzp.ticketmaster.controllers.LocationController;
import uj.jwzp.ticketmaster.entities.Location;
import uj.jwzp.ticketmaster.services.LocationService;

import java.util.ArrayList;
import java.util.List;



@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @MockBean
    private LocationService locationService;

    private LocationController locationController;

    @BeforeEach
    public void setup(){
        locationController = new LocationController(locationService);
    }

    @Test
    public void testGetLocations() {
        Location location1 = new Location("Tauron Arena");
        Location location2 = new Location("Ergo Arena");
        List<Location> locationList = new ArrayList<>();
        locationList.add(location1);
        locationList.add(location2);

        Mockito.when(locationService.getAllLocations()).thenReturn(locationList);

        Assertions.assertSame(locationController.getLocations(), locationList);
    }

    @Test
    public void testGetLocationsById() {
        Location location1 = new Location("Tauron Arena");
        Location location2 = new Location("Ergo Arena");

        Mockito.when(locationService.getLocationById(1)).thenReturn(location1);
        Mockito.when(locationService.getLocationById(2)).thenReturn(location2);

        Assertions.assertSame(locationController.getById(1).getBody(), location1);
        Assertions.assertSame(locationController.getById(2).getBody(), location2);

        Mockito.verify(locationService).getLocationById(1);
        Mockito.verify(locationService).getLocationById(2);
    }

    @Test
    public void testGetLocationsByIdNotFound() {
        Mockito.when(locationService.getLocationById(1)).thenReturn(null);

        ResponseEntity responseEntity = locationController.getById(1);
        Assertions.assertSame(responseEntity.getStatusCode(), HttpStatusCode.valueOf(404));

        Mockito.verify(locationService).getLocationById(1);
    }

}
